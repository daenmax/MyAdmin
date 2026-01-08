package cn.daenx.framework.notify.email.service.impl;

import cn.daenx.framework.common.domain.vo.system.config.SysEmailConfigVo;
import cn.daenx.framework.notify.email.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Properties;

@Service("email")
public class EmailServiceImpl implements EmailService {
    /**
     * 实际发送邮件协议
     *
     * @param email
     * @param toEmail  多个用,隔开
     * @param subject
     * @param content
     * @param isHtml   是否是HTML
     * @param fileList 附件内容，留空则无
     * @return
     */
    @Override
    public Boolean sendMail(SysEmailConfigVo.Email email, String toEmail, String subject, String content, Boolean isHtml, List<File> fileList) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(email.getHost());
        javaMailSender.setUsername(email.getEmail());
        javaMailSender.setPassword(email.getPassword());
        javaMailSender.setPort(email.getPort());
        javaMailSender.setDefaultEncoding(email.getEncode());
        javaMailSender.setProtocol(email.getProtocol());
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.timeout", email.getTimeout());
        properties.setProperty("mail.smtp.auth", email.getAuth());
        properties.setProperty("mail.smtp.socketFactoryClass", email.getSocketFactoryClass());
        javaMailSender.setJavaMailProperties(properties);
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper;
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(email.getFrom());
            mimeMessageHelper.setTo(toEmail.split(","));
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, isHtml);
            if (fileList != null) {
                for (File file : fileList) {
                    mimeMessageHelper.addAttachment(file.getName(), file);
                }
            }
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
