package com.kongbig.concurrent.test;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

/**
 * @author lianggangda
 * @date 2020/3/23 14:18
 */
public class Demo {

    public static ThreadPoolTaskExecutor taskExecutor() {
        // 机器核心数
        // corePoolSize = null == corePoolSize ? Runtime.getRuntime().availableProcessors() + 1 : corePoolSize;
        int corePoolSize = 3;
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(corePoolSize);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        // 设置队列容量
        executor.setQueueCapacity(20);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称前缀
        executor.setThreadNamePrefix("爬数据线程-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    public static void main(String[] args) {
        ThreadPoolTaskExecutor taskExecutor = taskExecutor();
        taskExecutor.initialize();

        int corePoolSize = 3;
        CountDownLatch countDownLatch = new CountDownLatch(corePoolSize);
        // 分页请求第三方网站
        for (int i = 1; i <= corePoolSize; i++) {
            final int a = i;
            CompletableFuture.supplyAsync(new Supplier<Boolean>() {
                @Override
                public Boolean get() {
                    if (a == 1) {
                        int j = 0;
                        while (j < 100000000) {
                            j++;
                        }
                    }
                    System.out.println(a);
                    return true;
                }
            }, taskExecutor).thenAcceptAsync(bool -> {
                countDownLatch.countDown();
                System.out.println("countDownLatch = " + countDownLatch.getCount());
            }).isCompletedExceptionally();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
