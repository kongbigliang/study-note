package com.kongbig.java8.service;

/**
 * @Description:
 * @author: lianggangda
 * @date: 2020/5/2 16:35
 */
@FunctionalInterface
public interface MyFunction2<T, R> {

    /**
     * @param t1
     * @param t2
     * @return
     */
    public R getValue(T t1, T t2);

}
