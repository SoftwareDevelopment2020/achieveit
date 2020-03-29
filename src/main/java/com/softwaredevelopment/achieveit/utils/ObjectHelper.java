package com.softwaredevelopment.achieveit.utils;

import com.softwaredevelopment.achieveit.utils.log.Logger;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;

/**
 * 对象工具类
 */
public class ObjectHelper {

    /**
     * 对象中的String类型的属性 空字符串设置为NULL
     */
    public static void setObjectEmptyToNul(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().equals(String.class)) {
                try {
                    field.setAccessible(true);
                    if (isEmpty(field.get(object))) {
                        field.set(object, null);
                    }
                } catch (Exception e) {
                    Logger.logError(e, "对象{}属性{}空字符串设置为NULL异常", object.getClass().getName(), field.getName());
                }
            }
        }
    }

    /**
     * 判断空值
     */
    public static boolean isEmpty(Object object) {
        if(object == null) {
            return true;
        }

        if (object instanceof String) {
            return ((String) object).trim().equals("");
        }

        return ObjectUtils.isEmpty(object);
    }

}
