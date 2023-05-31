package com.zhou.community;

import com.zhou.community.utils.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 发送text形式的邮件
     */
    @Test
    public void testTextMail(){
        mailClient.sendMail("2100271043@email.szu.edu.cn", "Test mail", "我要给你发邮件喽");
    }

    /**
     * 发送Html形式的邮件
     */
    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username", "小皮蛋");
        String process = templateEngine.process("/mail/demo", context);
        System.out.println(process);
        mailClient.sendMail("2100271043@email.szu.edu.cn", "Test Html", process);
    }
}
