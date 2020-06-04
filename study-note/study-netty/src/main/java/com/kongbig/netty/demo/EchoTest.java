package com.kongbig.netty.demo;

import org.junit.Test;

/**
 * @description:
 * @author: lianggangda
 * @date: 2020/6/1 11:12
 */
public class EchoTest {

    @Test
    public void testEchoServer() throws Exception {
        EchoServer server = new EchoServer(10999);
        server.start();
    }

    @Test
    public void testEchoClient() throws Exception {
        EchoClient client = new EchoClient("127.0.0.1", 10999);
        client.start();
    }

}
