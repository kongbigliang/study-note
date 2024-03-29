MQ：
* 消息队列（Message Queue，简称MQ），本质是个队列，FIFO先入先出，队列中存放的内容是message。
* 其主要用途：不同进程Process/线程Thread之间通信。
    * **系统解耦**；
    * **异步调用**；
    * **流量削峰**。
* 开发语言：ErLang – 面向并发的编程语言。
* AMQP是消息队列的一个协议（高级消息队列协议）。

5种队列<br>
![5种队列](./mq-img/5种队列.png "5种队列")
* 简单队列：
    > P：消息的生产者<br>
    > C：消息的消费者<br>
    > 红色：队列<br>
    > 生产者将消息发送到队列，消费者从队列中获取消息。

* Work模式：
    > 1个生产者、2个消费者<br>
    > 一个消息只能被一个消费者获取

* Work模式的“能者多劳”：
    ```
    // 同一时刻服务器只会发送一条消息给消费者
    channel.basicQos(1);
    
    // 开启这行 表示使用手动确认模式
    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    
    // 监听队列，false表示手动返回完成状态，true表示自动
    channel.basicConsume(QUEUE_NAME, false, consumer);
    ```

* 消息的确认模式：
    * 模式1：自动确认
        > 只要消息从队列中获取，无论消费者获取到消息后是否成功消息，都认为是消息已经成功消费。
        
        ![自动模式](./mq-img/自动模式.png "自动模式")
    * 模式2：手动确认
        > 消费者从队列中获取消息后，服务器会将该消息标记为不可用状态，等待消费者的反馈，如果消费者一直没有反馈，那么该消息将一直处于不可用状态。
        
        ![手动模式](./mq-img/手动模式.png "手动模式")

* 订阅模式：
    > 1个生产者，多个消费者<br>
    > 每一个消费者都有自己的一个队列<br>
    > 生产者没有将消息直接发送到队列，而是发送到了交换机<br>
    > 每个队列都要绑定到交换机<br>
    > 生产者发送的消息，经过交换机，到达队列，实现，一个消息被多个消费者获取的目的。

    > 注意：一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费
    
    ![订阅模式](./mq-img/订阅模式.png "订阅模式")

* 路由模式：

    ![路由模式](./mq-img/路由模式.png "路由模式")

* 主题模式（通配符模式）：
    > topic exchange将路由键和某模式进行匹配。此时队列需要绑定在一个模式上。<br>
    > “#”匹配一个或多个词，“*”匹配不多不少一个词。<br>
    > “audit.#”可以匹配“audit.irs.corporate”，“audit.*”只会匹配“audit.irs”
    
    ![主题模式](./mq-img/主题模式.png "主题模式")


用户角色：
* 超级管理员(administrator)
    > 可登陆管理控制台，可查看所有的信息，并且可以对用户，策略(policy)进行操作。
* 监控者(monitoring)
    > 可登陆管理控制台，同时可以查看rabbitmq节点的相关信息(进程数，内存使用情况，磁盘使用情况等)
* 策略制定者(policymaker)
    > 可登陆管理控制台, 同时可以对policy进行管理。但无法查看节点的相关信息(上图红框标识的部分)。
* 普通管理者(management)
    > 仅可登陆管理控制台，无法看到节点信息，也无法对策略进行管理。
* 其他
    > 无法登陆管理控制台，通常就是普通的生产者和消费者。

---
Spring-Rabbit
该项目由两部分组成；spring-amqp是基本抽象，spring-rabbit是RabbitMQ实现
* 用于异步处理入站消息的侦听器容器
* 发送和接收消息的RabbitTemplate
* RabbitAdmin自动声明队列，交换和绑定

    持久化：将交换机或队列的数据保存到磁盘，服务器宕机或重启之后依然存在。<br>
    非持久化：将交换机或队列的数据保存到内存，服务器宕机或重启之后将不存在。<br>
    非持久化的性能高于持久化。

![持久化交换机和队列](./mq-img/持久化交换机和队列.png "持久化交换机和队列")

---

Spring集成RabbitMQ一个完整案例
创建三个系统A,B,C
A作为生产者，B、C作为消费者(B,C作为web项目启动)
详情请看代码。
分别启动B,C两个web应用，然后运行A的MsgSender主方法发送消息，分别测试fanout、direct、topic三种类型

---
SpringBoot集成RabbitMQ：
springBoot集成RabbitMQ非常简单，如果只是简单的使用配置非常少，springBoot提供了spring-boot-starter-amqp对消息各种支持。


