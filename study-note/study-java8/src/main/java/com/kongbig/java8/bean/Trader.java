package com.kongbig.java8.bean;

import lombok.*;

/**
 * 交易员类
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Trader {

	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 城市
	 */
	private String city;

}
