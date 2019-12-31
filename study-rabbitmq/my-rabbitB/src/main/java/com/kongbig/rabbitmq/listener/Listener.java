package com.kongbig.rabbitmq.listener;

/**
 * 在B系统接收消息
 *
 * @author lianggangda
 * @date 2019/12/31 14:24
 */
public class Listener {

    /**
     * 具体执行业务的方法
     *
     * @param msg
     */
    public void listen(String msg) {
        System.out.println("\n消费者B开始处理消息: " + msg + "\n");
    }

}
