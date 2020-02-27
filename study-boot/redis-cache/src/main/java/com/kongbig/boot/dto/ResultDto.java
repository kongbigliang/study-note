package com.kongbig.boot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lianggangda
 * @date 2020/2/27 16:27
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ResultDto<T> success(T data) {
        return new ResultDto<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> ResultDto<T> success(T data, String message) {
        return new ResultDto<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> ResultDto<T> fail(String message) {
        return new ResultDto<T>(ResultCode.FAILED.getCode(), message, null);
    }

}
