package com.kongbig.base.regex;

import com.sun.org.apache.regexp.internal.RE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kongbig
 * @date 2020/2/22 16:00
 */
public class RegexMatches3 {

    private static final String REGEX = "foo";
    private static final String INPUT = "fooooooooooooooooo";
    private static final String INPUT2 = "ooooofoooooooooooo";

    private static Pattern pattern;
    private static Matcher matcher;
    private static Matcher matcher2;

    /**
     * matches和lookingAt方法都用来尝试匹配一个输入序列模式；
     * matches要求整个序列都匹配，而lookingAt不要求；
     * lookingAt需要从第一个字符开始匹配
     *
     * @param args
     */
    public static void main(String[] args) {
        pattern = Pattern.compile(REGEX);
        matcher = pattern.matcher(INPUT);
        matcher2 = pattern.matcher(INPUT2);

        System.out.println("Current REGEX is: " + REGEX);
        System.out.println("Current INPUT is: " + INPUT);
        System.out.println("Current INPUT2 is: " + INPUT2);


        System.out.println("lookingAt(): " + matcher.lookingAt());  // true
        System.out.println("matches(): " + matcher.matches());      // false
        System.out.println("lookingAt(): " + matcher2.lookingAt()); // true
    }

}
