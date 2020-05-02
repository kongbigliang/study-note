package com.kongbig.java8.bean;

import lombok.*;

/**
 * @Description:
 * @author: lianggangda
 * @date: 2020/5/2 10:25
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    private int id;
    private String name;
    private int age;
    private double salary;

}
