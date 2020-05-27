package com.kongbig.zk.demo01;

import com.netflix.curator.RetryPolicy;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.ExponentialBackoffRetry;
import com.sun.org.apache.xpath.internal.operations.String;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @author: lianggangda
 * @date: 2020/5/22 15:08
 */
public class TestCurator {

    private CuratorFramework client;

    /**
     * 使用Fluent风格api创建
     */
    @Before
    public void init() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                // 会话超时间
                .sessionTimeoutMs(5000)
                // 连接超时时间
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                // 包含隔离名称
                .namespace("base")
                .build();
        client.start();
    }

    /**
     * 创建数据节点
     *
     * @throws Exception
     */
    @Test
    public void createNode() throws Exception {
        client.create()
                // 递归创建所需父节点
                .creatingParentsIfNeeded()
                // 创建类型为持久节点
                .withMode(CreateMode.PERSISTENT)
                // 目录及内容
                .forPath("/nodeA", "init".getBytes());
    }

    /**
     * 读取数据节点
     *
     * @throws Exception
     */
    @Test
    public void getData() throws Exception {
        byte[] bytes1 = client.getData().forPath("/nodeA");
        System.out.println(bytes1);

        // 读stat
        byte[] bytes2 = client.getData()
                .storingStatIn(new Stat())
                .forPath("/nodeA");
        System.out.println(bytes2);
    }

    /**
     * 删除数据节点
     *
     * @throws Exception
     */
    @Test
    public void deleteNode() throws Exception {
        client.delete()
                // 强制保证删除
                .guaranteed()
                // TODO 递归删除子节点
                // 指定删除的版本号
                // .withVersion(10086)
                .forPath("/nodeA");
    }

    /**
     * 修改数据节点
     *
     * @throws Exception
     */
    @Test
    public void updateData() throws Exception {
        client.setData()
                // 指定版本修改
                // .withVersion(10086)
                .forPath("/nodeA", "data".getBytes());
    }

    /**
     * 事务
     *
     * @throws Exception
     */
    @Test
    public void tra() throws Exception {
        client.inTransaction().check().forPath("/nodeA")
                .and()
                .create().withMode(CreateMode.EPHEMERAL).forPath("/nodeB", "init".getBytes())
                .and()
                .create().withMode(CreateMode.EPHEMERAL).forPath("/nodeC", "init".getBytes())
                .and()
                .commit();
    }

    /**
     * 判断节点是否存在
     *
     * @throws Exception
     */
    @Test
    public void exist() throws Exception {
        Stat stat = client.checkExists().forPath("/nodeA");
        System.out.println(stat);

        // 获取子节点的路径
        List<java.lang.String> childrens = client.getChildren().forPath("/nodeA");
        childrens.forEach(System.out::println);
    }

    /**
     * 异步回调
     *
     * @throws Exception
     */
    @Test
    public void asyncCallBack() throws Exception {
        Executor executor = Executors.newFixedThreadPool(2);
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground((curatorFramework, curatorEvent) -> {
                    System.out.println("eventType:" + curatorEvent.getType() + ", resultCode:" + curatorEvent.getResultCode());
                }, executor)
                .forPath("path");
    }

}
