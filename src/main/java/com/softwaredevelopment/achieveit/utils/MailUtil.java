package com.softwaredevelopment.achieveit.utils;

import com.softwaredevelopment.achieveit.entity.MailBean;
import com.softwaredevelopment.achieveit.utils.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;


/**
 * MailUtil
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-03-05 20:42
 */
@Component
public class MailUtil {
    @Value("${spring.mail.username}")
    private String MAIL_SENDER; //邮件发送者

    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * 发送文本邮件
     *
     * @param mailBean
     */
    public void sendSimpleMail(MailBean mailBean) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(MAIL_SENDER);
            mailMessage.setTo(mailBean.getRecipient());
            mailMessage.setSubject(mailBean.getSubject());
            mailMessage.setText(mailBean.getContent());
            //mailMessage.copyTo(copyTo);

            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            Logger.logError("邮件发送失败", e.getMessage());
        }
    }

    public void sendHTMLMail(MailBean mailBean) {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            //true 表示需要创建一个multipart message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(MAIL_SENDER);
            mimeMessageHelper.setTo(mailBean.getRecipient());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            //邮件抄送
            //mimeMessageHelper.addCc("抄送人");
            mimeMessageHelper.setText(mailBean.getContent(), true);
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            Logger.logError("邮件发送失败", e.getMessage());
        }
    }
}
