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

}
