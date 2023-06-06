### v0.1 到 v0.2 版本的说明
1. readxml方法从资源文件读取内容并存入beanDefinitions，这件事情有两个地方不确定，资源的来源不同、资源的格式不同，抽象的Resource的接口，它的不同子类从不同的来源读取， 但是最终都是以Resource接口的形式提供给外部访问的，这样解决了第一个不确定来源的问题； 但是resource接口中被迭代的object又是根据不同格式不同而不同的， element只是xml格式的，所以又定义了BeanDefinitionReader接口， 它的不同子类可以读取不同格式的资源来形成beanDefinition 。
2. instanceBeans方法取消了 。
3. getBean方法功能增强了，不仅是获得bean，对于未创建的bean还要创建bean
4. 新的applicationContext负责组装，可以根据它的名字来体现它的组装功能，例如ClassPathXmlApplicationContext  它组装的Resource的实现类是ClassPathXmlResource  ，然后因为是xml的，所以需要BeanDefinitionReader的实现类XmlBeanDefinitionReader来读取并注册进beanFactory，同时ApplicationContext也提供了getBean底层调用beanfactory的实现，提供了registerBeanDefinition  来向底层的beanFactory注册bean。5 beanFactory 提供了registerBeanDefinition和getBean接口，这样无论是applicationContext还是beanDefinitionReader都可以向它注册beanDefinition，只要向它注册了，就可以调用它的getBean方法，我一直很纠结为什么不是beanfactory调用不同的beanDefinitionReader，写完这些，好像有点理解了，这样beanfactory就很专注自己的getBean方法，别的组件要怎么注入，它都不管了。
![](.note_images/6d94f58b.png)

### v0.2 到 v0.3 版本的说明
1、增加单例 Bean 的接口定义，然后把所有的 Bean 默认为单例模式。
2、预留事件监听的接口，方便后续进一步解耦代码逻辑。
3、扩展 BeanDefinition，添加一些属性，现在它只有 id 和 class 两个属性
![](.note_images/233ae3a8.png)

### v0.3 到 v0.4 版本的说明
1、Spring 如何解析<property>和<constructor-arg>标签。、
2、构造器注入
3、setter注入
4、Bean 之间的依赖注入
5、解决循环依赖
![](.note_images/e719ab16.png)