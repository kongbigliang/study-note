package com.kongbig.netty.test;

import org.junit.Test;

/**
 * 测试开启多个webSocket服务端
 *
 * @author kongbig
 * @date 2019/11/9 10:13
 */
public class MockWsTest {

    @Test
    public void startWsServer8888() throws InterruptedException {
        MockWsServer mockWsServer = new MockWsServer(8888);
        mockWsServer.start();
    }

    @Test
    public void startWsServer9999() throws InterruptedException {
        MockWsServer mockWsServer = new MockWsServer(9999);
        mockWsServer.start();
    }

    @Test
    public void startWsServer11111() throws InterruptedException {
        MockWsServer mockWsServer = new MockWsServer(11111);
        mockWsServer.start();
    }

}
