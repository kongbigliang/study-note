SpringCloud：
SpringCloud是一个基于SpringBoot实现的微服务架构开发工具。它为微服务架构中涉及的配置管理、服务治理、断路器、智能路由、控制总线、全局锁、决策竞选 、分布式会话和集群状态管理等操作提供了一种简单的开发方式。
---

Eureka：
Eureka是Netflix开源的服务发现组件，本身是一个基于REST的服务。它包含Server和Client两部分。SpringCloud将它继承在子项目SpringCloud Netflix中，从而实现微服务的注册和发现。
---

Ribbon：
Ribbon是Netflix发布的负载均衡器，它有助于控制HTTP和TCP客户端的行为。为Ribbon配置服务提供者地址列表后，Ribbon就基于某种负载均衡算法，自动地帮助服务消费者去请求。Ribbon默认为我们提供很多的负载均衡算法，例如轮询、随机等。也可为Ribbon实现自定义的负载均衡算法。 
![ribbon架构](./cloud-img/ribbon架构.png "ribbon架构")

设置负载均衡策略
只需要在配置文件中添加配置
serviceId.ribbon.NFLoadBalancerRuleClassName=自定义的负载均衡策略类
接口：
    IRule
实现类：
    AbstractLoadBalancerRule：负载均衡策略的抽象类，定义了负载均衡器ILoadBalancer对象（策略模式）
    RandomRule：随机策略。new Random().nextInt(serverCount);
    PredicateBasedRule：默认策略，综合判断服务所在区得性能和服务的可用性。
    RoundRobinRule：轮询策略。
    RetryRule：重试机制策略。封装了RoundRobinRule，并在指定尝试结束时间前，再次获取服务实例。
---

Hystrix：
容错保护。防止级联失败，对延迟和故障提供容错能力。
雪崩效应：多个服务之间调用，基础服务的故障可能会导致级联故障，进而造成整个系统不可用的情况。
![hystrix雪崩效应](./cloud-img/hystrix雪崩效应.png "hystrix雪崩效应")
实现延迟容错：
- 包裹请求：使用HystrixCommand/HystrixObservableCommand包裹对依赖的调用逻辑，每个命令再独立线程中执行。
- 跳闸机制：某服务的错误率超过某一阈值时，Hystrix自动或手动跳闸，停止请求该服务一段时间。
- 资源隔离：Hystrix为每个依赖都维护了一个小型的线程池（信号量）。如果该线程池已满，发往该依赖的请求就被立即拒绝，而不是排队等候，从而加速失败判定。
- 监控：Hystrix可以近乎实时地监控运行指标和配置的变化（如成功、失败、超时、被拒绝的请求等）。
- 回退机制：当请求失败、超时、被拒绝，或当断路器打开时，执行回退逻辑。回退逻辑可由开发人员自行提供，例如返回缺省值。
- 自我修复：断路器打开一段时间后，会自动进入“半开”状态。断路器打开、关闭、半开的逻辑转换。
原理说明：
![hystrix原理](./cloud-img/hystrix原理.png "hystrix原理")
当对特定服务的呼叫达到一定阈值时（Hystrix中的默认值为5秒内的20次故障），电路打开，不进行通讯。并且是一个隔离的线程中进行的。
---

Feign:
Feign是声明式、模块化的HTTP客户端。SpringCloud对Feign进行了增强，使Feign支持了SpringMVC注解，并整合了Ribbon和Eureka。
设置统一的hystrix fallback接口
- 不在方法上使用@HystrixCommand注解
- 创建回调类
- 在Feign客户端中添加fallback属性
- 配置文件中开启hystrix
---

SpringCloudZuul：
Zuul是Netflix开源的微服务网关，它可以和Eureka、Ribbon、Hystrix等组件配合使用。
Zuul的核心是一系列的过滤器，完成了一下功能：
- 身份认证与安全：
- 审查与监控：
- 动态路由：
- 压力测试：逐渐增加指向集群的流量，以了解性能。
- 负载分配：
- 静态响应处理：在边缘位置直接建立部分相应，从而避免其转发到内部集群。
- 多区域弹性：

SpringCloud对Zuul进行了整合与增强。目前，Zuul默认使用HTTP客户端是Apache HTTP Client；
如果想使用RestClient，可以设置ribbon.restclient.enabled=true；
想要使用okhttp3.OkHttpClient，可以设置ribbon.okhttp.enabled=true；
使用Zuul之后的架构：
![使用Zuul之后的架构](./cloud-img/使用Zuul之后的架构.png "使用Zuul之后的架构")

zuul配置详解：
- 指定服务id
zuul.routes.指定微服务的serviceId = 指定路径即可，如：
zuul:
    routes:
        ms-provider-user: /user/**
这样设置，ms-provider-user微服务就会被映射到/user/**路径。

- 忽略指定服务：
zuul:
    ignored-services: ms-provider-user,ms-consumer-movie
这样设置，zuul忽略ms-provider-user和ms-consumer-movie两个微服务。

- 忽略所有服务，只是由路由指定：
zuul:
    ignored-services: '*'   # 使用'*'可忽略所有微服务
    routes: 
        ms-provider-user: /user/**
这样设置，可以只路由ms-provider-user微服务

- 同时配置path和url：
zuul:
    routes:
        # 该配置方式中，user-route只是给路由一个名称，可以任意起名。
        user-route:
            url: http://localhost:8000/     # 指定的url
            path: /user/**                  # url对应的路径
这样就可以将path映射到url。（不能使用Ribbon来负载多个url）

- 面向服务配置，不破坏Hystrix、Ribbon特性：
zuul:
    routes: # 定义服务转发规则
        item-service: # item-service这个名字是任意写的
            path: /item-service/**      # 匹配item-service的url路径请求app-item服务
            serviceid: app-item
        order-service: # 名字尽量和业务系统相关
            path: /order-service/**     # 匹配order-service的url路径请求app-order服务
            serviceid: app-order        # 指定Eureka注册中心的服务id
        
- 使用正则表达式指定路由规则：
```
@Bean
public PatternServiceRouteMapper serviceRouteMapper() {
    // 调用构造函数
    // public PatternServiceRouteMapper(String servicePattern, String routePattern)
    // servicePattern指定微服务的正则
    // routePattern指定路由正则
    return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)", 
            "${version}/${name}")
}
```
通过这段代码可实现诸如ms-provider-user-v1这个微服务，映射到/v1/ms-provider-user/**这个路径。

- 路由前缀：
zuul:
    prefix: /api
    strip-prefix: false
    routes:
        ms-provider-user: /user/**
这样访问zuul的/api/ms-provider-user/1路径，请求会被转发到ms-provider-user的/api/1
zuul:
    routes:
        ms-provider-user:
            path: /user/**
            strip-prefix: false
这样访问zuul的/user/1路径，请求会被转发到ms-provider-user的/user/1。

- 忽略某些路径：
zuul:
    ignoredPatterns: /**/admin/**   # 忽略所有包含/admin/的路径
    routes:
        ms-provider-user: /user/**
这样可将ms-provider-user微服务映射到/user/**的路径，但会忽略该微服务中包含/admin/的路径。

过滤器ZuulFilter：
ZuulFilter是一个抽象类，其实现类需要实现4个方法：
- shouldFilter：返回一个Boolean值，判断该过滤器是否需要执行。返回true执行，返回false不执行。
- run：过滤器的具体业务逻辑。
- filterType：返回字符串代表过滤器的类型
    - pre：请求在被路由之前执行
    - routing：在路由请求时调用
    - post：在routing和error过滤器之后调用
    - error：处理请求时发生错误调用
- filterOrder：通过返回的int值来定义过滤器的执行顺序，数字越小优先级越高。
![ZuulFilter执行流程](./cloud-img/ZuulFilter执行流程.png "ZuulFilter执行流程")

默认开启ribbon：
Zuul网关默认开启了 ribbon 负载均衡，可以修改端口，启动多个Item服务进行测试。

动态网关：
将yml配置文件中的路由配置信息存在配置中心中可以实现网关的动态配置。

网关集群：
Zuul可以配合nginx搭建网关集群。只要在nginx的配置文件nginx.conf里配置多个zuul地址：
```markdown
http {
  	upstream myhttpServer {
            # 配置多个zuul地址
	    server localhost:8087;
	    server localhost:8086;
	}
	server {
        listen       80;
        server_name  www.kongbig.com;
        location / {
                proxy_pass   http://myhttpServer;
                index  index.html index.htm;
            }
	}
}
```

---
分布式配置中心 Spring Cloud Config
为分布式系统外部化配置提供了服务器端和客户端的支持，它包含了Config Server和Config Client两部分。
Config Server和Config Client都实现了对Spring Environment和PropertySource抽象的映射。

Config Server是一个可横向扩展、集中式的配置服务器，它用于集中管理应用程序各个环境下的配置，默认使用Git存储配置文件内容，也可以使用SVN存储，或者是本地文件存储。
Config Client是Config Server的客户端，用于操作存储在Config Server中的配置内容。微服务在启动时会请求Config Server获取配置文件的内容，请求到后再启动容器。

请求配置文件的规则如下：
```markdown
/{application}/{profile}/[label]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```
application相当于spring.application.name

为Config Client添加refresh支持：
使用actuator 监控中心完成刷新功能，Bean加上@RefreshScope注解
手动发起post请求actuator接口：
    POST请求监控中心 http://127.0.0.1:8081/actuator/refresh
    
借助与git的WebHooks（web钩子）实现自动更新：
码云、github等git服务器提供了web hook功能，意思是，在仓库中的资源发生更新时会通知给谁，这里的谁是一个url地址。
![WebHooks更新配置的流程](./cloud-img/WebHooks更新配置的流程.png "WebHooks更新配置的流程")

---

Spring Cloud Bus消息总线：
目前Spring Cloud Bus消息总线只是实现了对RabbitMQ以及Kafka的支持。
![Spring Cloud Bus的架构](./cloud-img/Spring Cloud Bus的架构.png "Spring Cloud Bus的架构")

流程总结：（8082也是item服务）
更新文件到git服务器，Git服务器通过web钩子通知到8081的/bus/refresh，8081的实例将消息发送到springCloudBus的交换机，由于8081的队列页绑定到交换机，所以8082也获取到了更新的通知，然后去Config Server获取最新的数据。

架构优化：
在前面的架构中，8081这个实例不仅仅是提供了商品查询的服务，还负责发送更新的消息到RabbitMQ。
违反了微服务架构中的职责单一的原则。
改进：将原有的Config Server不仅仅提供配置查询的服务，而且还要负责更新消息的发送。web hook通知ConfigServer
修改Git中的web钩子，直接通知ConfigServer
![架构优化图](./cloud-img/架构优化图.png "架构优化图")

---

整合zookeeper作为注册中心：
参考项目。
通过ZooInspector查看注册的服务。

--- 

整合consul作为注册中心：
有点难玩，不搞了，接下来学习Spring Cloud Alibaba

--- 

整合swagger API管理：
启动工程，访问http://localhost:8081/swagger-ui.html

Zuul整合Swagger统一管理微服务Api：
在一个入口统一管理所有微服务(订单服务、商品服务等)项目的API。
实现方式：zuul。

