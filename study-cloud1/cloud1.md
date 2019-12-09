SpringCloud：
SpringCloud是一个基于SpringBoot实现的微服务架构开发工具。它为微服务架构中涉及的配置管理、服务治理、断路器、智能路由、控制总线、全局锁、决策竞选 、分布式会话和集群状态管理等操作提供了一种简单的开发方式。

Eureka：
Eureka是Netflix开源的服务发现组件，本身是一个基于REST的服务。它包含Server和Client两部分。SpringCloud将它继承在子项目SpringCloud Netflix中，从而实现微服务的注册和发现。

Ribbon：
Ribbon是Netflix发布的负载均衡器，它有助于控制HTTP和TCP客户端的行为。为Ribbon配置服务提供者地址列表后，Ribbon就基于某种负载均衡算法，自动地帮助服务消费者去请求。Ribbon默认为我们提供很多的负载均衡算法，例如轮询、随机等。也可为Ribbon实现自定义的负载均衡算法。 
![](/cloud-img/ribbon架构.jpg "ribbon架构")

