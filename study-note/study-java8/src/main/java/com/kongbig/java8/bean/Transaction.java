package com.kongbig.java8.bean;

import lombok.*;

/**
 * 交易类
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {

    /**
     * 交易员
     */
    private Trader trader;
    /**
     * 年
     */
    private int year;
    /**
     * 交易额
     */
    private int value;

}