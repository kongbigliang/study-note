package com.kongbig.java8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * lambda表达式：一段带有输入参数的可执行语句块
 *
 * @author kongbig
 * @date 2019/11/2 18:47
 */
public class TestLambda01 {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("hello", "java8", "lambda");
        names.sort((o1, o2) -> o1.compareTo(o2));
        names.forEach(s -> System.out.println(s));

        names.sort(String::compareTo);
        names.forEach(System.out::println);
    }

}
