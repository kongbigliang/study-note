package com.kongbig.java8.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * @Description:
 * @author: lianggangda
 * @date: 2020/5/2 10:17
 */
public class TestLambda {

    /**
     * 原来的匿名内部类
     */
    @Test
    public void test1() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };

        TreeSet<Integer> ts = new TreeSet<>(comparator);
    }

    /**
     * Lambda表达式
     */
    @Test
    public void test2() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        TreeSet<Integer> ts = new TreeSet<>(comparator);
    }

}
