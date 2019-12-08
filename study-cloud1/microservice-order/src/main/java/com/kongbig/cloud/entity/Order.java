package com.kongbig.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author kongbig
 * @date 2019/12/7 22:23
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderId;

    private Long userId;

    private Date createDate;

    private Date updateDate;

    private List<OrderDetail> orderDetails;

}
