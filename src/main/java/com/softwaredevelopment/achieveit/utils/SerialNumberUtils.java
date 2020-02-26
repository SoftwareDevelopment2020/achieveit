package com.softwaredevelopment.achieveit.utils;

public class SerialNumberUtils {

    public static String UUID() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }

}
