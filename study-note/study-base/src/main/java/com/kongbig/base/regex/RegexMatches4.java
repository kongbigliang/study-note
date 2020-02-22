package com.kongbig.base.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kongbig
 * @date 2020/2/22 16:50
 */
public class RegexMatches4 {

    private static String REGEX = "dog";
    private static String INPUT = "The dog says meow. All Dogs sag meow.";
    private static String REPLACE = "cat";

    /**
     * replaceFirst 和 replaceAll 方法用来替换匹配正则表达式的文本。
     * 不同的是，replaceFirst 替换首次匹配，replaceAll 替换所有匹配。
     *
     * @param args
     */
    public static void main(String[] args) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT);
        String result = m.replaceAll(REPLACE);
        System.out.println(result);
    }

}
