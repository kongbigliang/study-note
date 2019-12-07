package com.kongbig.java8.stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Stream：
 * 是元素的集合。
 * 可以支持顺序和并行的对原Stream进行汇聚的操作。
 *
 * @author kongbig
 * @date 2019/11/2 19:03
 */
public class TestStream01 {

    /**
     * 使用Stream的基本步骤：
     * 1.创建Stream；
     * 2.转换Stream，每次转换原有Stream对象不改变，返回一个新的Stream对象（**可以有多次转换**）；
     * 3.对Stream进行聚合（Reduce）操作，获取想要的结果；
     *
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> numbs = Lists.newArrayList(1, null, 3, 4, null, 6, 6);
        System.out.println("不为空的元素个数：" + numbs.stream().filter(num -> null != num).count());

        System.out.println("------------");

        List<Integer> filter = numbs.stream().filter(Objects::nonNull).collect(Collectors.toList());
        filter.forEach(System.out::println);

        System.out.println("------------");

        // distinct: 对于Stream中包含的元素进行去重操作（去重逻辑依赖元素的equals方法）。
        List<Integer> distinct = numbs.stream().distinct().collect(Collectors.toList());
        distinct.forEach(System.out::println);

        System.out.println("------------");

        // map: 对于Stream中包含的元素使用给定的转换函数进行转换操作，新生成的Stream只包含转换生成的元素。
        // 这个方法有三个对于原始类型的变种方法，分别是：mapToInt，mapToLong和mapToDouble。
        numbs.stream().filter(Objects::nonNull).mapToDouble(s -> s).forEach(System.out::println);

        System.out.println("------------");

        //  flatMap：和map类似，不同的是其每个元素转换得到的是Stream对象，会把子Stream中的元素压缩到父集合中

        // peek: 生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数（Consumer实例），
        // 新Stream每个元素被消费的时候都会执行给定的消费函数

        //  limit: 对一个Stream进行截断操作，获取其前N个元素，如果原Stream中包含的元素个数小于N，那就获取其所有的元素;

        //  limit: 对一个Stream进行截断操作，获取其前N个元素，如果原Stream中包含的元素个数小于N，那就获取其所有的元素;

        List<Integer> numbs2 = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        System.out.println("sum is: " + numbs2.stream()
                .filter(num -> num != null)
                .distinct()
                .mapToInt(num -> num * 2)
                // 再每个元素被消费的时候打印自身
                .peek(System.out::println)
                .skip(2)
                .limit(4)
                .sum());


    }

}




