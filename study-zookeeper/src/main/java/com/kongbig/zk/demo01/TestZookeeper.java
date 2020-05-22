package com.kongbig.zk.demo01;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @Description:
 * @author: lianggangda
 * @date: 2020/5/22 14:02
 */
@Slf4j
public class TestZookeeper {

    // private static final String CONNECT_STRING = "hadoop102:2181,hadoop103:2181,hadoop104:2181";

    private static final String CONNECT_STRING = "127.0.0.1:2181";
    private static final int SESSION_TIME = 20000;
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(CONNECT_STRING, SESSION_TIME, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                log.warn(event.toString());
            }
        });
    }

    /**
     * 创建节点
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void createNode() throws KeeperException, InterruptedException {
        /*
            org.apache.zookeeper.CreateMode可以设置znode是否为EPHEMERAL或者SEQUENTIAL。可以为下面四种值:
            PERSISTENT：持久化目录znode
            PERSISTENT_SEQUENTIAL：顺序自动编号的目录znode。这个目录节点是根据当前已存在的节点数递增。
            EPHEMERAL：临时目录znode，一旦创建这个znode的客户端和服务器断开，这个节点就会自动删除。临时节点（EPHEMERAL）不能有子节点数据
            EPHEMERAL_SEQUENTIAL：临时自动编号znode。
         */
        String path = zkClient.create("/kongbig", "helloZk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }

    /**
     * 获取子节点，并监控节点的变化
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void getDataAndWatch() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/", true);
        children.forEach(System.out::println);

        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 判断节点是否存在
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void exist() throws KeeperException, InterruptedException {
        Stat stat = zkClient.exists("/kongbig", false);
        System.out.println(Objects.isNull(stat) ? "不存在" : "存在");
    }

}
