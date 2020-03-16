package com.softwaredevelopment.achieveit.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * MailBean
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-03-05 20:38
 */
@Data
public class MailBean implements Serializable {
    private static final long serialVersionUID = -2116367492649751914L;
    /**
     * 收件人
     */
    private String recipient;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;
}
