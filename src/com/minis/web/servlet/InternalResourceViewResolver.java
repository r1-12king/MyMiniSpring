package com.minis.web.servlet;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-15 16:52
 **/
public class InternalResourceViewResolver implements ViewResolver{
    private Class<?> viewClass = null;
    private String viewClassName = "";
    private String prefix = "";
    private String suffix = "";
    private String contentType;

    public InternalResourceViewResolver() {
        if (getViewClass() == null) {
            setViewClass(JstlView.class);
        }
    }

    protected String getViewClassName() {
        return this.viewClassName;
    }

    public void setViewClassName(String viewClassName) {
        this.viewClassName = viewClassName;
        Class<?> clz = null;
        try {
            clz = Class.forName(viewClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setViewClass(clz);
    }

    protected Class<?> getViewClass() {
        return this.viewClass;
    }

    public void setViewClass(Class<?> viewClass) {
        this.viewClass = viewClass;
    }

    protected String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = (prefix != null ? prefix : "");
    }

    protected String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = (suffix != null ? suffix : "");
    }

    protected String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public View resolveViewName(String viewName) throws Exception {
        return buildView(viewName);
    }

    protected View buildView(String viewName) throws Exception {
        Class<?> viewClass = getViewClass();

        View view = (View) viewClass.newInstance();
        view.setUrl(getPrefix() + viewName + getSuffix());

        String contentType = getContentType();
        view.setContentType(contentType);

        return view;
    }
}
