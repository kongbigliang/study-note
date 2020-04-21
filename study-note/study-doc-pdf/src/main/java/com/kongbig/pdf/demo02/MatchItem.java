package com.kongbig.pdf.demo02;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用来保存关键字信息
 */
@Setter
@Getter
@ToString
public class MatchItem {

    /**
     * 页数
     */
    private Integer pageNum;
    /**
     * x坐标
     */
    private Float x;
    /**
     * y坐标
     */
    private Float y;
    /**
     * 页宽
     */
    private Float pageWidth;
    /**
     * 页高
     */
    private Float pageHeight;
    /**
     * 匹配字符
     */
    private String content;
    /**
     * 字体宽
     */
    private float fontWidth;
    /**
     * 字体高
     */
    private float fontHeight = 12;

}