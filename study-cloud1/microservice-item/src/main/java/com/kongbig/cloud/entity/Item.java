package com.kongbig.cloud.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author kongbig
 * @date 2019/12/7 14:37
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Long id;

    private String title;

    private String pic;

    private String desc;

    private Long price;

}
