package com.kongbig.java8.forkjoin;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;

/**
 * @Description:
 * @author: lianggangda
 * @date: 2020/5/4 15:03
 */
public class TestForkJoin {

    private static final long COUNT = 100000000L;

    /**
     * 用ForkJoin框架计算500亿的累加
     */
    @Test
    public void test1() {
        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinCalculate task = new ForkJoinCalculate(0, COUNT);
        Long sum = pool.invoke(task);

        Instant end = Instant.now();
        // 约105毫秒
        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());
    }

    /**
     * 普通for（单线程）
     */
    @Test
    public void test2() {
        Instant start = Instant.now();

        long sum = 0L;
        for (long i = 0; i < COUNT; i++) {
            sum += i;
        }

        Instant end = Instant.now();
        // 约152毫秒
        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());
    }

}
