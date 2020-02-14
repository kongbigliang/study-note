package com.kongbig.boot.controller;

import com.kongbig.boot.service.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lianggangda
 * @date 2020/2/14 15:23
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private IAsyncService asyncServiceImpl;

    @GetMapping("/submit")
    public String submit() {
        log.info("start submit");

        // 调用service层的任务
        asyncServiceImpl.executeAsync();

        log.info("end submit");

        return "success";
    }

}
