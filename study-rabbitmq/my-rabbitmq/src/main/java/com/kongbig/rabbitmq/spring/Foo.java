package com.kongbig.rabbitmq.spring;

/**
 * 消费者
 *
 * @author lianggangda
 * @date 2019/12/30 14:28
 */
public class Foo {

    /**
     * 具体执行业务的方法
     *
     * @param foo
     */
    public void listen(String foo) {
        System.out.println("\n消费者: " + foo + "\n");
    }

}
