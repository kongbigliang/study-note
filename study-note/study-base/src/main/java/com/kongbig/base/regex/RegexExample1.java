package com.kongbig.base.regex;

import java.util.regex.Pattern;

/**
 * @author kongbig
 * @date 2020/2/22 14:50
 */
public class RegexExample1 {

    /**
     * Pattern 类：
     * pattern 对象是一个正则表达式的编译表示。
     * Pattern 类没有公共构造方法。
     * 要创建一个 Pattern 对象，你必须首先调用其公共静态编译方法，它返回一个 Pattern 对象。
     * 该方法接受一个正则表达式作为它的第一个参数。
     * <p>
     * <p>
     * Matcher 类：
     * Matcher 对象是对输入字符串进行解释和匹配操作的引擎。
     * 与Pattern 类一样，Matcher 也没有公共构造方法。
     * 你需要调用 Pattern 对象的 matcher 方法来获得一个 Matcher 对象。
     * <p>
     * <p>
     * PatternSyntaxException：
     * PatternSyntaxException 是一个非强制异常类，它表示一个正则表达式模式中的语法错误。
     *
     * @param args
     */
    public static void main(String[] args) {
        String content = "I am apple from www.baidu.com";

        String pattern = ".*baidu.*";
        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了 'baidu' 字符串？" + isMatch);
    }

}
