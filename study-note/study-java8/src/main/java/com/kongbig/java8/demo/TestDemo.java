package com.kongbig.java8.demo;

import com.kongbig.java8.bean.Employee;
import com.kongbig.java8.service.MyPredicate;
import org.junit.Test;

import java.util.*;

/**
 * @Description:
 * @author: lianggangda
 * @date: 2020/5/2 10:17
 */
public class TestDemo {

    List<Employee> employees = Arrays.asList(
            new Employee(101, "张三", 18, 9999.99),
            new Employee(102, "李四", 59, 6666.66),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

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

    /**
     * 优化方式1：策略模式
     */

    /**
     * 优化方式2：匿名内部类
     */
    @Test
    public void test3() {
        List<Employee> list = filterEmployee(employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee emp) {
                return emp.getSalary() <= 5000;
            }
        });

        for (Employee employee : list) {
            System.out.println(employee);
        }
    }

    private List<Employee> filterEmployee(List<Employee> list, MyPredicate<Employee> mp) {
        List<Employee> result = new ArrayList<>(list.size());
        for (Employee employee : list) {
            if (mp.test(employee)) {
                result.add(employee);
            }
        }
        return result;
    }

    /**
     * 优化方式3：Lambda表达式
     */
    @Test
    public void test4() {
        List<Employee> list = filterEmployee(this.employees, (e) -> e.getSalary() >= 5000);
        list.forEach(System.out::println);
    }

    /**
     * 优化方式4：StreamApi
     */
    @Test
    public void test5() {
        employees.stream()
                .filter((e) -> e.getSalary() >= 5000)
                .limit(2)
                .forEach(System.out::println);

        System.out.println("-------------------------------------");

        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

    }
}
