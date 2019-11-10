package com.kongbig.ws.service;

import com.google.common.collect.Sets;
import com.kongbig.ws.netty.AsyncWsClient;
import io.netty.bootstrap.Bootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * webSocket通道service
 * 此类应该写在业务系统当中，业务系统将本module作为依赖导进来
 *
 * @author kongbig
 * @date 2019/11/10 9:58
 */
@Service
public class CustomWsChannelService extends BaseWsChannelService {

    @Autowired
    @Qualifier("bootstrap")
    private Bootstrap bootstrap;

    /**
     * 自定义回调
     */
    @Autowired
    private CustomAsyncWsCallBack customAsyncWsCallBack;

    @PostConstruct
    @Override
    public void initWsConnection() {
        /**
         * TODO 查询出所有需要连接的ws服务
         * 一般可以是连接有webSocket服务的硬件设备；如人脸设备、签字板等；
         * 先模拟：
         */
        Set<String> wsServerIpPortSet = Sets.newHashSet("127.0.0.1:8888", "127.0.0.1:9999", "127.0.0.1:11111");
        wsServerIpPortSet.forEach(wsServerIpPort -> {
            String[] split = wsServerIpPort.split(":");
            String ip = split[0];
            int port = Integer.parseInt(split[1]);
            this.createConnection(ip, port);
        });
    }

    @Override
    public void removeWsConnection(String ip, int port) {

    }

    @Override
    public void addWsConnection(String ip, int port) {

    }

    /**
     * 创建连接
     *
     * @param ip   ip
     * @param port 端口
     */
    private AsyncWsClient createConnection(String ip, int port) {
        AsyncWsClient asyncWsClient = null;
        String wsUrl = super.getWebSocketUrlStr(ip, port);
        if (asyncWsClientMap.containsKey(wsUrl)) {
            asyncWsClient = asyncWsClientMap.get(wsUrl);
            asyncWsClient.disconnect();
            asyncWsClient.setWsUrl(wsUrl);
            asyncWsClient.setIp(ip);
            asyncWsClient.setPort(port);
            asyncWsClient.setAsyncWsCallBack(customAsyncWsCallBack);
        } else {
            asyncWsClient = new AsyncWsClient(ip, port, wsUrl, customAsyncWsCallBack, bootstrap);
            asyncWsClientMap.put(wsUrl, asyncWsClient);
        }
        asyncWsClient.connect();
        return asyncWsClient;
    }

}
