package com.kongbig.boot.controller;

import com.kongbig.boot.dto.ResultDto;
import com.kongbig.boot.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 会员登录注册管理Controller
 *
 * @author lianggangda
 * @date 2020/2/27 16:23
 */
@RestController
@RequestMapping("/sso")
@Api(tags = "UmsMemberController", description = "会员登录注册管理")
public class UmsMemberController {

    @Autowired
    private UmsMemberService memberService;

    /**
     * 获取验证码
     *
     * @param telephone
     * @return
     */
    @GetMapping("/getAuthCode")
    @ResponseBody
    @ApiOperation("获取验证码")
    public ResultDto getAuthCode(@RequestParam String telephone) {
        return memberService.generateAuthCode(telephone);
    }

    /**
     * 判断验证码是否正确
     *
     * @param telephone
     * @param authCode
     * @return
     */
    @PostMapping("/verifyAuthCode")
    @ResponseBody
    public ResultDto updatePassword(@RequestParam String telephone,
                                    @RequestParam String authCode) {
        return memberService.verifyAuthCode(telephone, authCode);
    }

}
