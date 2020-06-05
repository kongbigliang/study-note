package com.kongbig.ws.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: lianggangda
 * @date: 2020/6/5 13:40
 */
@Setter
@Getter
@Builder
public class User {

    private String id;
    private String username;
    private Integer age;

}
