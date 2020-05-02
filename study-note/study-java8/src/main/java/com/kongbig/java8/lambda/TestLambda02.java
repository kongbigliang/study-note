package com.kongbig.java8.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @Description:
 * @author: lianggangda
 * @date: 2020/5/2 17:03
 */
public class TestLambda02 {

    /**
     * Consumer<T> 消费型接口：
     */
    @Test
    public void test1() {
        happy(10000, (m) -> System.out.println("买买买 买了" + m + "元"));
    }

    public void happy(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    /**
     * Supplier<T> 供给型接口：
     */
    @Test
    public void test2() {
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));

        for (Integer num : numList) {
            System.out.println(num);
        }
    }

    /**
     * 产生指定个数的整数，并放入集合中。
     *
     * @param num
     * @param sup
     * @return
     */
    public List<Integer> getNumList(int num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }

        return list;
    }

    /**
     * Function<T, R> 函数型接口：
     */
    @Test
    public void test3() {
        String newStr = strHandler("\t\t\t 我吃个大西瓜   ", (str) -> str.trim());
        System.out.println(newStr);

        String subStr = strHandler("我吃个大西瓜", (str) -> str.substring(2, 5));
        System.out.println(subStr);
    }

    /**
     * 需求：用于处理字符串
     *
     * @param str
     * @param fun
     * @return
     */
    public String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    /**
     * Predicate<T> 断言型接口：
     */
    @Test
    public void test4() {
        List<String> list = Arrays.asList("Hello", "kongbig", "Lambda", "www", "ok");
        List<String> strList = filterStr(list, (s) -> s.length() > 3);

        for (String str : strList) {
            System.out.println(str);
        }
    }

    /**
     * 需求：将满足条件的字符串，放入集合中
     *
     * @param list
     * @param pre
     * @return
     */
    public List<String> filterStr(List<String> list, Predicate<String> pre) {
        List<String> strList = new ArrayList<>();

        for (String str : list) {
            if (pre.test(str)) {
                strList.add(str);
            }
        }

        return strList;
    }

}
