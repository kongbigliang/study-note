package com.kongbig.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author kongbig
 * @date 2019/12/7 22:23
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    private String orderId;

    private Item item = new Item();

}
