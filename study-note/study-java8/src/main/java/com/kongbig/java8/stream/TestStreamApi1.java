package com.kongbig.java8.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Description: 创建 Stream
 * @author: lianggangda
 * @date: 2020/5/3 11:14
 */
public class TestStreamApi1 {

    /**
     * 1.创建 Stream
     * 4种方式
     */
    @Test
    public void test1() {
        // 1. Collection 提供了两个方法  stream() 与 parallelStream()
        List<String> list = new ArrayList<>();
        // 获取一个顺序流
        Stream<String> stream = list.stream();
        // 获取一个并行流
        Stream<String> parallelStream = list.parallelStream();

        // 2. 通过 Arrays 中的 stream() 获取一个数组流
        Integer[] nums = new Integer[10];
        Stream<Integer> stream1 = Arrays.stream(nums);

        // 3. 通过 Stream 类中静态方法 of()
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5, 6);

        // 4. 创建无限流
        // 迭代
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(10);
        stream3.forEach(System.out::println);

        // 生成
        Stream<Double> stream4 = Stream.generate(Math::random).limit(2);
        stream4.forEach(System.out::println);
    }

}
