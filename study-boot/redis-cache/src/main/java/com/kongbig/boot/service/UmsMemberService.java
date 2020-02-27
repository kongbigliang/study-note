package com.kongbig.boot.service;

import com.kongbig.boot.dto.ResultDto;

/**
 * @author lianggangda
 * @date 2020/2/27 16:25
 */
public interface UmsMemberService {

    /**
     * 生成验证码
     *
     * @param telephone
     * @return
     */
    ResultDto generateAuthCode(String telephone);


    /**
     * 判断验证码和手机号码是否匹配
     *
     * @param telephone
     * @param authCode
     * @return
     */
    ResultDto verifyAuthCode(String telephone, String authCode);

}
