package com.kongbig.ws.controller;

import com.kongbig.ws.dto.UserDto;
import com.kongbig.ws.entity.User;
import com.kongbig.ws.netty.MyNettyWebSocket;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @description:
 * @author: lianggangda
 * @date: 2020/6/5 13:34
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    private static List<User> list;

    static {
        getFromDB();
    }

    private static void getFromDB() {
        list = new ArrayList<>();
        list.add(User.builder().id("80559265").username("kongbig").age(25).build());
        list.add(User.builder().id("99999").username("张三").age(26).build());
        list.add(User.builder().id("10000").username("李四").age(27).build());
    }

    @GetMapping("/sendMsgToClient")
    public void sendMsgToClient(UserDto dto) {
        String userId = dto.getId();
        if (StringUtils.isBlank(userId)) {
            log.error("请输入用户id");
            return;
        }

        Session session = MyNettyWebSocket.USER_SESSION_MAP.get(userId);
        Optional<User> first = list.stream().filter(e -> e.getId().equals(userId)).findFirst();
        User user = first.get();
        session.sendText(String.format("用户id为[%s]的年龄为[%d]", userId, user.getAge()));
    }

}
