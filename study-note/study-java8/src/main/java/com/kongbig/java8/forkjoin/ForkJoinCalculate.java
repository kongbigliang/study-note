package com.kongbig.java8.forkjoin;

import lombok.AllArgsConstructor;

import java.util.concurrent.RecursiveTask;

/**
 * Recursive：递归循环
 *
 * @Description: fork、join实现累加
 * @author: lianggangda
 * @date: 2020/5/4 14:54
 */
@AllArgsConstructor
public class ForkJoinCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    /**
     * 临界值
     */
    private static final long THRESHOLD = 10000;

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THRESHOLD) {
            // 小于临界值，直接计算。
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;

            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            // 拆分子任务，同时压入线程队列
            left.fork();

            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
            right.fork();

            return left.join() + right.join();
        }
    }

}
