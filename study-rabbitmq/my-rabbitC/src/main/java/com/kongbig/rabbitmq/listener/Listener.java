package com.kongbig.rabbitmq.listener;

/**
 * 在C系统中接收消息
 * （和B系统配置差不多，无非是Q名和Q对应的处理逻辑变了）
 *
 * @author lianggangda
 * @date 2019/12/31 15:45
 */
public class Listener {

    /**
     * 具体执行业务的方法
     *
     * @param msg
     */
    public void listen(String msg) {
        System.out.println("\n消费者C开始处理消息: " + msg + "\n");
    }

}
