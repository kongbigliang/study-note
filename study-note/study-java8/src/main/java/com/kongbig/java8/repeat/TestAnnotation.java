package com.kongbig.java8.repeat;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @Description: 重复注解与类型注解
 * @author: lianggangda
 * @date: 2020/5/4 17:32
 */
public class TestAnnotation {
    
    @Test
    public void test1() throws NoSuchMethodException {
        Class<TestAnnotation> clazz = TestAnnotation.class;
        Method m1 = clazz.getMethod("show", String.class);

        MyAnnotation[] mas = m1.getAnnotationsByType(MyAnnotation.class);

        for (MyAnnotation ma : mas) {
            System.out.println(ma.value());
        }
    }
    
    @MyAnnotation("Hello")
    @MyAnnotation("World")
    public void show(@MyAnnotation("abc") String str){
        
    }
    
}
