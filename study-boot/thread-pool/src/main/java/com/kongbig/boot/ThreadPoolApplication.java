package com.kongbig.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 访问：http://127.0.0.1:8080/test/submit
 * 可观察异步效果（流量削峰）
 *
 * @author lianggangda
 * @date 2020/2/14 15:54
 */
@SpringBootApplication
public class ThreadPoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadPoolApplication.class, args);
    }

}
