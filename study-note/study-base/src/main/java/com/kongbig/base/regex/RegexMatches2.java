package com.kongbig.base.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对单词“cat”在输入字符串中出现次数进行计数的例子
 *
 * @author kongbig
 * @date 2020/2/22 15:47
 */
public class RegexMatches2 {

    private static final String REGEX = "\\bcat\\b";
    private static final String INPUT = "cat cat cattie cat";

    public static void main(String[] args) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT);
        int count = 0;

        while (m.find()) {
            count++;
            System.out.println("Match number " + count);
            System.out.println("start(): " + m.start());
            System.out.println("end(): " + m.end());
        }
    }

}
