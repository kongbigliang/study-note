package com.kongbig.boot.service.impl;

import com.kongbig.boot.dto.ResultDto;
import com.kongbig.boot.service.RedisService;
import com.kongbig.boot.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.ref.PhantomReference;
import java.util.Random;

/**
 * @author lianggangda
 * @date 2020/2/27 16:46
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String redisKeyPrefixAuthCode;
    @Value("${redis.key.expire.authCode}")
    private Long authCodeExpireSeconds;


    @Override
    public ResultDto generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0, len = 6; i < len; i++) {
            sb.append(random.nextInt(10));
        }
        // 验证码绑定手机号并存储到redis
        String key = redisKeyPrefixAuthCode + telephone;
        redisService.set(key, sb.toString());
        redisService.expire(key, authCodeExpireSeconds);
        return ResultDto.success(sb.toString(), "获取验证码成功");
    }

    @Override
    public ResultDto verifyAuthCode(String telephone, String authCode) {
        if (StringUtils.isEmpty(authCode)) {
            return ResultDto.fail("请输入验证码");
        }
        String realAuthCode = redisService.get(redisKeyPrefixAuthCode + telephone);
        boolean result = authCode.equals(realAuthCode);
        if (result) {
            return ResultDto.success(null, "验证码校验成功");
        } else {
            return ResultDto.fail("验证码不正确");
        }
    }

}
