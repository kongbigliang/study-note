package com.kongbig.boot.service.impl;

import com.kongbig.boot.service.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author lianggangda
 * @date 2020/2/14 15:18
 */
@Service
@Slf4j
public class AsyncServiceImpl implements IAsyncService {

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        log.info("start executeAsync");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("end executeAsync");
    }

}
