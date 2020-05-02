package com.kongbig.java8.lambda;

import com.kongbig.java8.service.MyFun;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @Description:
 * @author: lianggangda
 * @date: 2020/5/2 15:22
 */
public class TestLambda01 {

    @Test
    public void test1() {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World!");
            }
        };
        r1.run();

        System.out.println("------------------");

        Runnable r2 = () -> System.out.println("Hello Lambda!");
        r2.run();
    }

    @Test
    public void test2() {
        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("哈哈哈哈~");
    }

    @Test
    public void test3() {
        Comparator<Integer> comparator = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
    }

    @Test
    public void test4() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
    }

    @Test
    public void test5() {
        Integer num = operation(100, (x) -> x * x);
        System.out.println(num);

        System.out.println(operation(200, (y) -> y + 200));
    }

    public Integer operation(Integer num, MyFun mf) {
        return mf.getValue(num);
    }

}
