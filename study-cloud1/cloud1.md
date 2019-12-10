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
