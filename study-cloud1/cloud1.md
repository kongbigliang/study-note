SpringCloud：
SpringCloud是一个基于SpringBoot实现的微服务架构开发工具。它为微服务架构中涉及的配置管理、服务治理、断路器、智能路由、控制总线、全局锁、决策竞选 、分布式会话和集群状态管理等操作提供了一种简单的开发方式。
---

Eureka：
Eureka是Netflix开源的服务发现组件，本身是一个基于REST的服务。它包含Server和Client两部分。SpringCloud将它继承在子项目SpringCloud Netflix中，从而实现微服务的注册和发现。
---

Ribbon：
Ribbon是Netflix发布的负载均衡器，它有助于控制HTTP和TCP客户端的行为。为Ribbon配置服务提供者地址列表后，Ribbon就基于某种负载均衡算法，自动地帮助服务消费者去请求。Ribbon默认为我们提供很多的负载均衡算法，例如轮询、随机等。也可为Ribbon实现自定义的负载均衡算法。 
![ribbon架构](/cloud-img/ribbon架构.png "ribbon架构")

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
![hystrix雪崩效应](/cloud-img/hystrix雪崩效应.png "hystrix雪崩效应")
实现延迟容错：
- 包裹请求：使用HystrixCommand/HystrixObservableCommand包裹对依赖的调用逻辑，每个命令再独立线程中执行。
- 跳闸机制：某服务的错误率超过某一阈值时，Hystrix自动或手动跳闸，停止请求该服务一段时间。
- 资源隔离：Hystrix为每个依赖都维护了一个小型的线程池（信号量）。如果该线程池已满，发往该依赖的请求就被立即拒绝，而不是排队等候，从而加速失败判定。
- 监控：Hystrix可以近乎实时地监控运行指标和配置的变化（如成功、失败、超时、被拒绝的请求等）。
- 回退机制：当请求失败、超时、被拒绝，或当断路器打开时，执行回退逻辑。回退逻辑可由开发人员自行提供，例如返回缺省值。
- 自我修复：断路器打开一段时间后，会自动进入“半开”状态。断路器打开、关闭、半开的逻辑转换。
原理说明：
![hystrix原理](/cloud-img/hystrix原理.png "hystrix原理")
当对特定服务的呼叫达到一定阈值时（Hystrix中的默认值为5秒内的20次故障），电路打开，不进行通讯。并且是一个隔离的线程中进行的。





