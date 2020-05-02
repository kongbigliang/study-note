package com.kongbig.java8.service;

import com.kongbig.java8.bean.Employee;

/**
 * @Description:
 * @author: lianggangda
 * @date: 2020/5/2 10:37
 */
public interface MyPredicate<T> {

    /**
     * 比较方法
     *
     * @param emp
     * @return
     */
    public boolean test(Employee emp);

}
