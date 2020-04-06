package com.softwaredevelopment.achieveit.Utils;

import com.softwaredevelopment.achieveit.entity.MailBean;
import com.softwaredevelopment.achieveit.utils.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * MailTest
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-03-05 20:50
 */
@SpringBootTest
@Transactional
public class MailTest {
    //接收人
    private static final String RECIPINET = "rainkq@gmail.com";
    @Autowired
    private MailUtil mailUtil;

    /**
     * 发送文本邮件
     */
    @Test
    public void sendSimpleMail() throws ExecutionException, InterruptedException {
        MailBean mailBean = new MailBean();
        mailBean.setRecipient(RECIPINET);
        mailBean.setSubject("测试发mail");
        mailBean.setContent("SpringBootMail发送一个简单格式的邮件，时间为：" + (new Date()));

        Future<String> stringFuture = mailUtil.sendSimpleMail(mailBean);
        // 等待发送完成
        while (!stringFuture.isDone()) ;
        System.out.println(stringFuture.get());
    }
}
