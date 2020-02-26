package com.softwaredevelopment.achieveit.utils;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaredevelopment.achieveit.utils.log.Logger;
import lombok.extern.slf4j.Slf4j;

/**
 * json序列化/反序列化工具类
 */
@Slf4j
public class JsonUtils {

    /**
     * 延迟单例工具类
     */
    private static class InstanceUtils {
        /**
         * ObjectMapper单例
         */
        private static ObjectMapper instance = new ObjectMapper();
    }

    /**
     * 将对象序列化为字符串, 使用自定义logger打印
     */
    public static String toJSONString(Object object) {
        return toJSONString(object, false);
    }

    /**
     * 将对象序列化为字符串
     * @param useSlf4j 是否使用slf4j打印，自定义Logger调用时使用slf4j打印
     */
    public static String toJSONString(Object object, boolean useSlf4j) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return (String) object;
        }

        try {
            return InstanceUtils.instance.writeValueAsString(object);
        } catch (Exception e) {
            if (useSlf4j) {
                Logger.slf4jLogError("JSON序列化异常", e);
            } else {
                Logger.logError(e, "JSON序列化异常");
            }
            return null;
        }
    }

    /**
     * 将字符串反序列化为对象, 使用自定义logger打印
     */
    public static <T> T toObject(String value, Class<T> clazz) {
        return toObject(value, clazz, false);
    }

    /**
     * 将字符串反序列化为对象, 使用自定义logger打印
     * @param useSlf4j 是否使用slf4j打印，自定义Logger调用时使用slf4j打印
     */
    public static <T> T toObject(String value, Class<T> clazz, boolean useSlf4j) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        try {
            return InstanceUtils.instance.readValue(value, clazz);
        } catch (Exception e) {
            if (useSlf4j) {
                Logger.slf4jLogError("JSON反序列化异常", e);
            } else {
                Logger.logError(e, "JSON反序列化异常");
            }
            return null;
        }
    }

}
