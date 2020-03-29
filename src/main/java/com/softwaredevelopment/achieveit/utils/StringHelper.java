package com.softwaredevelopment.achieveit.utils;

import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringHelper {

    public static String format(String message, Object... args) {
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }

    public static List<String> stringsDivideByCommaToList(String string) {
        return new ArrayList<String>(Arrays.asList(string.split(",")));
    }

    /**
     * 判断字符串 1. 为NULL 2. 为空串 3. 只有空格
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

}
