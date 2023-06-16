package com.minis.web.servlet;

import com.minis.beans.BeansException;
import com.minis.web.AnnotationConfigWebApplicationContext;
import com.minis.web.MappingValue;
import com.minis.web.RequestMapping;
import com.minis.web.WebApplicationContext;
import com.minis.web.XmlScanComponentHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
    public static final String HANDLER_MAPPING_BEAN_NAME = "handlerMapping";
    public static final String HANDLER_ADAPTER_BEAN_NAME = "handlerAdapter";
    public static final String VIEW_RESOLVER_BEAN_NAME = "viewResolver";
    private static final long serialVersionUID = 1L;
    // 用于存储controller名称和对象之间的映射关系
    private final Map<String, Object> controllerObjs = new HashMap<>();
    // 用于存储controller名称和类之间的映射关系
    private final Map<String, Class<?>> controllerClasses = new HashMap<>();
    // 保存自定义的requestMapping 名称的列表
    private final List<String> urlMappingNames = new ArrayList<>();
    /**
     * url 和 方法的映射关系
     */
    private final Map<String, Method> mappingMethods = new HashMap<>();
    private final Map<String, Class<?>> mappingClz = new HashMap<>();
    /**
     * url 和 对象的映射关系
     */
    private final Map<String, Object> mappingObjs = new HashMap<>();
    /**
     * web上下文环境
     */
    private WebApplicationContext webApplicationContext;
    private WebApplicationContext parentApplicationContext;
    // 存储需要扫描的package列表
    private List<String> packageNames = new ArrayList<>();
    private String sContextConfigLocation;

    // 保存controller名称数组列表
    private List<String> controllerNames = new ArrayList<>();
    private Map<String, MappingValue> mappingValues;

    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;

    private ViewResolver viewResolver;


    public DispatcherServlet() {
        super();
    }

    protected void initHandlerMappings(WebApplicationContext wac) {
        try {
            this.handlerMapping = (HandlerMapping) wac.getBean(HANDLER_MAPPING_BEAN_NAME);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    protected void initHandlerAdapters(WebApplicationContext wac) {
        try {
            this.handlerAdapter = (HandlerAdapter) wac.getBean(HANDLER_ADAPTER_BEAN_NAME);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    protected void initViewResolvers(WebApplicationContext wac) {
        try {
            this.viewResolver = (ViewResolver) wac.getBean(VIEW_RESOLVER_BEAN_NAME);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    /**
     * init：在servlet创建时调用，默认第一次访问的时候被调用，但也可以通过配置可以实现服务器启动时调用，创建的对象会被缓存起来。
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // 改造 DispatcherServlet，关联 WAC
        this.parentApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        // 后期配置文件的位置和名称  -- servlet参数
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");

        this.webApplicationContext = new AnnotationConfigWebApplicationContext(sContextConfigLocation, this.parentApplicationContext);

        refresh();
    }


    /**
     * 初始化配置
     */
    protected void refresh() {
        // 初始化 controller
//        initController();
        initHandlerMappings(this.webApplicationContext);
        initHandlerAdapters(this.webApplicationContext);
        initViewResolvers(this.webApplicationContext);
    }


    /**
     * 同来替代doGet()方法
     *
     * @param request
     * @param response
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
        try {
            doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest processedRequest = request;
        HandlerMethod handlerMethod = null;
        ModelAndView mv = null;

        handlerMethod = this.handlerMapping.getHandler(processedRequest);
        if (handlerMethod == null) {
            return;
        }

        HandlerAdapter ha = this.handlerAdapter;

        mv = ha.handle(processedRequest, response, handlerMethod);

        render(processedRequest, response, mv);
    }

    protected void render(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {
        if (mv == null) {
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }

        String sTarget = mv.getViewName();
        Map<String, Object> modelMap = mv.getModel();
        View view = resolveViewName(sTarget, modelMap, request);
        view.render(modelMap, request, response);

    }

    protected View resolveViewName(String viewName, Map<String, Object> model,
                                   HttpServletRequest request) throws Exception {
        if (this.viewResolver != null) {
            View view = viewResolver.resolveViewName(viewName);
            return view;
        }
        return null;
    }


    /**
     * 重写doGet 方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String sPath = request.getServletPath();
//        System.out.println(sPath);
//        if (!this.urlMappingNames.contains(sPath)) {
//            return;
//        }
//
//        Object obj = null;
//        Object objResult = null;
//        try {
//            Method method = this.mappingMethods.get(sPath);
//            obj = this.mappingObjs.get(sPath);
//            objResult = method.invoke(obj);
//        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//
//        assert objResult != null;
//        response.getWriter().append(objResult.toString());
//    }
//
//
//    /**
//     * 重写doPost 方法
//     *
//     * @param request
//     * @param response
//     * @throws ServletException
//     * @throws IOException
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }


    /**
     * 初始化controller，并保存映射关系
     */
    protected void initController() {
        //扫描包，获取所有类名
        this.controllerNames = scanPackages(this.packageNames);
        for (String controllerName : this.controllerNames) {
            Object obj = null;
            Class<?> clz = null;
            try {
                //加载类
                clz = Class.forName(controllerName);
                this.controllerClasses.put(controllerName, clz);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                //实例化bean
                assert clz != null;
                obj = clz.newInstance();
                this.controllerObjs.put(controllerName, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取类的名称
     *
     * @param packages
     * @return
     */
    private List<String> scanPackages(List<String> packages) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packages) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URI uri = null;
        //将以.分隔的包名换成以/分隔的uri
        try {
            uri = this.getClass().getResource("/" +
                    packageName.replaceAll("\\.", "/")).toURI();
        } catch (Exception e) {
        }
        File dir = new File(uri);
        //处理对应的文件目录
        //目录下的文件或者子目录
        for (File file : dir.listFiles()) {
            //对子目录递归扫描
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            }//类文件
            else {
                String controllerName = packageName + "."
                        + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }


    protected void initMapping() {
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                //检查所有的方法
                boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                //有RequestMapping注解
                if (isRequestMapping) {
                    String methodName = method.getName();
                    //建立方法名和URL的映射
                    String urlMapping = method.getAnnotation(RequestMapping.class).value();
                    this.urlMappingNames.add(urlMapping);
                    this.mappingObjs.put(urlMapping, obj);
                    this.mappingMethods.put(urlMapping, method);
                }
            }
        }
    }
}