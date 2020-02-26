package com.softwaredevelopment.achieveit.utils;

import org.slf4j.helpers.MessageFormatter;

public class StringHelper {

    public static String format(String message, Object... args) {
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }
}
