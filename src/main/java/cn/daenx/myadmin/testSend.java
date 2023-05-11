package cn.daenx.myadmin;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class testSend {
    public static void main(String[] args) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.qq.com");
        javaMailSender.setUsername("1330166564@qq.com");
        javaMailSender.setPassword("ewdubopyrcmyjhgd");
        javaMailSender.setPort(587);
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setProtocol(JavaMailSenderImpl.DEFAULT_PROTOCOL);
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.timeout", "25000");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactoryClass", "javax.net.ssl.SSLSocketFactory");
        javaMailSender.setJavaMailProperties(properties);

        //1、创建邮件对象（设置参数后提交）
        SimpleMailMessage message = new SimpleMailMessage();

        //2、设置主题
        message.setSubject("测试发送邮件3");
        //3、设置邮件发送者
        message.setFrom("MyAdmin<1330166564@qq.com>");
        //4、设置邮件接受者，多个接受者传参为数组格式
        String toEmail = "1330166565@qq.com,wangjingen163@163.com";
        message.setTo(toEmail.split(","));
        //5、设置邮件正文（邮件的正式内容）
        message.setText("这是一封测试邮件，收到即为正常");

        javaMailSender.send(message);
    }
}
