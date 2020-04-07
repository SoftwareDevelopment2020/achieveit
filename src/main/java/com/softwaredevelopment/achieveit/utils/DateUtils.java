package com.softwaredevelopment.achieveit.utils;

import java.time.LocalDateTime;

/**
 * 日期工具类
 */
public class DateUtils {

    /**
     * 判断日期是否在区间内
     * @param leftEqual 左区间是否闭合
     * @param left 左区间
     * @param rightEqual 右区间是否闭合
     * @param right 右区间
     * @param time 需要判断的时间
     * @return
     */
    private static boolean isBetween(boolean leftEqual, LocalDateTime left, boolean rightEqual, LocalDateTime right, LocalDateTime time) {
        return (!leftEqual || time.isEqual(left)) && (!rightEqual || time.isEqual(right)) && time.isAfter(left) && time.isBefore(right);
    }

    public static boolean isBetweenLeftEqual(LocalDateTime left, LocalDateTime right, LocalDateTime time) {
        return isBetween(true, left, false, right, time);
    }

    public static boolean isBetweenRightEqual(LocalDateTime left, LocalDateTime right, LocalDateTime time) {
        return isBetween(false, left, true, right, time);
    }

    public static boolean isBetweenBothEqual(LocalDateTime left, LocalDateTime right, LocalDateTime time) {
        return isBetween(true, left, true, right, time);
    }

    public static boolean isBetween(LocalDateTime left, LocalDateTime right, LocalDateTime time) {
        return isBetween(false, left, false, right, time);
    }

    /**
     * return time <= max
     */
    public static boolean isBeforeOrEqual(LocalDateTime max, LocalDateTime time) {
        return time.isBefore(max) || time.isEqual(max);
    }

    /**
     * return time >= min
     */
    public static boolean isAfterOrEqual(LocalDateTime min, LocalDateTime time) {
        return time.isAfter(min) || time.isEqual(min);
    }
}
