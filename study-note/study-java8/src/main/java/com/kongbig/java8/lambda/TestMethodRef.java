package com.kongbig.java8.lambda;

import com.kongbig.java8.bean.Employee;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/**
 * @Description:
 * @author: lianggangda
 * @date: 2020/5/2 22:09
 */
public class TestMethodRef {

    /**
     * 对象的引用 :: 实例方法名
     */
    @Test
    public void test1() {
        PrintStream ps = System.out;
        Consumer<String> consumer1 = (str) -> ps.println(str);
        consumer1.accept("Hello World！");

        System.out.println("-------------------------");

        Consumer<String> consumer2 = ps::println;
        consumer2.accept("Hello Java8");

        System.out.println("-------------------------");

        Consumer<String> consumer3 = System.out::println;
        consumer3.accept("emmm...");
    }

    @Test
    public void test2() {
        Employee emp = new Employee(101, "张三", 18, 9999.99);
        Supplier<String> sup1 = () -> emp.getName();
        System.out.println(sup1.get());

        System.out.println("-------------------------");

        Supplier<String> sup2 = emp::getName;
        System.out.println(sup2.get());
    }

    /**
     * 类名 :: 静态方法名
     */
    @Test
    public void test3() {
        BiFunction<Double, Double, Double> fun = (x, y) -> Math.max(x, y);
        System.out.println(fun.apply(1.5, 22.2));

        System.out.println("-------------------------");

        BiFunction<Double, Double, Double> fun2 = Math::max;
        System.out.println(fun2.apply(1.2, 1.5));
    }


    @Test
    public void test4() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

        System.out.println("-------------------------");

        Comparator<Integer> com2 = Integer::compare;
    }

    /**
     * 类名 :: 实例方法名
     */
    @Test
    public void test5() {
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        System.out.println(bp.test("abcde", "abcde"));

        System.out.println("-------------------------");

        BiPredicate<String, String> bp2 = String::equals;
        System.out.println(bp2.test("abc", "abc"));

        System.out.println("-------------------------");


        Function<Employee, String> fun = (e) -> e.show();
        System.out.println(fun.apply(new Employee()));

        System.out.println("-------------------------");

        Function<Employee, String> fun2 = Employee::show;
        System.out.println(fun2.apply(new Employee()));
    }

    /**
     * 构造器引用
     */
    @Test
    public void test6() {
        Supplier<Employee> sup = () -> new Employee();
        System.out.println(sup.get());

        System.out.println("-------------------------");

        Supplier<Employee> sup2 = Employee::new;
        System.out.println(sup2.get());
    }

    @Test
    public void test7() {
        Function<String, Employee> fun = Employee::new;

        BiFunction<String, Integer, Employee> fun2 = Employee::new;
    }

    /**
     * 数组引用
     */
    @Test
    public void test8() {
        Function<Integer, String[]> fun = (x) -> new String[x];
        String[] strs = fun.apply(10);
        System.out.println(strs.length);

        System.out.println("-------------------------");

        Function<Integer, Employee[]> fun2 = Employee[]::new;
        Employee[] emps = fun2.apply(20);
        System.out.println(emps.length);
    }

}
