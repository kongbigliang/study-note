package com.kongbig.base.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kongbig
 * @date 2020/2/22 16:55
 */
public class RegexMatches5 {

    private static String REGEX = "a*b";
    private static String INPUT = "aabfooaabfooabfoobkkk";
    private static String REPLACE = "-";

    /**
     * Matcher 类也提供了appendReplacement 和 appendTail 方法用于文本替换
     *
     * @param args
     */
    public static void main(String[] args) {
        Pattern p = Pattern.compile(REGEX);
        // 获取matcher对象
        Matcher m = p.matcher(INPUT);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, REPLACE);
        }
        m.appendTail(sb);
        System.out.println(sb.toString());
    }

}
