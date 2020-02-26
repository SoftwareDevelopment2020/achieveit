package com.softwaredevelopment.achieveit.utils.log;

import lombok.Data;

import java.util.Date;

/**
 * 日志实体类
 */
@Data
public class LogEntity {

    /**
     * 操作用户
     */
    private String OPER_USER;

    /**
     * 操作IP
     */
    private String OPER_IP;

    /**
     * 信息
     */
    private String MESSAGE;

    /**
     * 时间
     */
    private Date TIME = new Date();

    /**
     * 日志输出类名
     */
    private String CLASS_NAME = Thread.currentThread() .getStackTrace()[4].getClassName();

    /**
     * 日志输出方法名
     */
    private String METHOD_NAME = Thread.currentThread() .getStackTrace()[4].getMethodName();

}
