package com.kongbig.study.demo01;

/**
 * 创建一个名为Calculator的类，它实现了 Runnable接口
 *
 * @author kongbig
 * @date 2019/11/18 20:22
 */
public class Calculator implements Runnable {

    private int number;

    public Calculator(int number) {
        this.number = number;
    }

    /**
     * 这个方法用来执行我们创建的线程的指令，它将对指定的数字进行乘法表运算。
     */
    @Override
    public void run() {
        for (int i = 0; i <= 10; i++) {
            System.out.printf("%s:\t%d * %d = %d\n", Thread.currentThread().getName(), number, i, number * i);
        }
    }

}
