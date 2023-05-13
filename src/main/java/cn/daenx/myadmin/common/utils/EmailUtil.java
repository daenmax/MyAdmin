package cn.daenx.myadmin.common.utils;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysConfig;
import cn.daenx.myadmin.system.vo.system.SysEmailConfigVo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import jakarta.mail.internet.MimeMessage;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Email工具类
 *
 * @author DaenMax
 */
@Component
public class EmailUtil {

    /**
     * 邮箱轮询队列脚本
     */
    private static RedisScript<String> nextEmailScript;

    @Resource
    public void setNextEmailScript(RedisScript<String> nextEmailScript) {
        EmailUtil.nextEmailScript = nextEmailScript;
    }


    /**
     * 发送邮件
     * 按照系统邮箱配置的使用模式进行选择邮箱号
     *
     * @param toEmail  多个用,隔开
     * @param subject
     * @param content
     * @param isHtml   是否是HTML
     * @param fileList 附件内容，留空则无
     * @return
     */
    public static Boolean sendEmail(String toEmail, String subject, String content, Boolean isHtml, List<File> fileList) {
        SysEmailConfigVo.Email email = getOneEmailConfig();
        if (ObjectUtil.isEmpty(email)) {
            return false;
        }
        return sendMailProtocol(email, toEmail, subject, content, isHtml, fileList);
    }

    /**
     * 发送邮件
     * 指定邮箱号
     *
     * @param toEmail   多个用,隔开
     * @param subject
     * @param content
     * @param isHtml    是否是HTML
     * @param fileList  附件内容，留空则无
     * @param fromEmail 指定要使用的在系统配置里的发信邮箱
     * @return
     */
    public static Boolean sendEmail(String toEmail, String subject, String content, Boolean isHtml, List<File> fileList, String fromEmail) {
        SysEmailConfigVo.Email email = getOneEmailConfig(fromEmail);
        if (ObjectUtil.isEmpty(email)) {
            return false;
        }
        return sendMailProtocol(email, toEmail, subject, content, isHtml, fileList);
    }

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
    private static Boolean sendMailProtocol(SysEmailConfigVo.Email email, String toEmail, String subject, String content, Boolean isHtml, List<File> fileList) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(email.getHost());
        javaMailSender.setUsername(email.getHost());
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

    /**
     * 从redis里获取系统邮箱配置信息
     * 不存在或者被禁用或者数量为0返回null
     *
     * @return
     */
    private static SysEmailConfigVo getSysEmailConfigVo() {
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + "sys.email.config");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
            return null;
        }
        SysEmailConfigVo sysEmailConfigVo = JSONObject.parseObject(sysConfig.getValue(), SysEmailConfigVo.class);
        if (sysEmailConfigVo.getEmails().size() == 0) {
            return null;
        }
        return sysEmailConfigVo;
    }

    /**
     * 获得一个邮箱对象
     * 不存在或者被禁用或者数量为0返回null
     *
     * @return
     */
    private static SysEmailConfigVo.Email getOneEmailConfig() {
        SysEmailConfigVo sysEmailConfigVo = getSysEmailConfigVo();
        if (ObjectUtil.isEmpty(sysEmailConfigVo)) {
            return null;
        }
        List<SysEmailConfigVo.Email> list = sysEmailConfigVo.getEmails().stream().filter(item -> "true".equals(item.getEnable())).collect(Collectors.toList());
        if (list.size() == 1) {
            return sysEmailConfigVo.getEmails().get(0);
        }
        SysEmailConfigVo.Email oneEmail = getOneEmailByMode(sysEmailConfigVo.getConfig(), list);
        return oneEmail;
    }

    /**
     * 获得一个邮箱对象
     * 不存在或者被禁用或者数量为0返回null
     *
     * @param email 指定邮箱
     * @return
     */
    private static SysEmailConfigVo.Email getOneEmailConfig(String email) {
        SysEmailConfigVo sysEmailConfigVo = getSysEmailConfigVo();
        if (ObjectUtil.isEmpty(sysEmailConfigVo)) {
            return null;
        }
        List<SysEmailConfigVo.Email> list = sysEmailConfigVo.getEmails().stream().filter(item -> "true".equals(item.getEnable()) && email.equals(item.getEmail())).collect(Collectors.toList());
        if (list.size() > 0) {
            return sysEmailConfigVo.getEmails().get(0);
        }
        return null;
    }

    /**
     * 邮箱使用模式_轮询时，弹出一个邮箱并重新放进去
     *
     * @return
     */
    private static String rightPopAndLeftPushEmail() {
        //这里两种方式，自己决定用哪一种哈~
        //使用redis lua方式
        String email = (String) RedisUtil.getRedisTemplate().execute(nextEmailScript, CollUtil.newArrayList(SystemConstant.EMAIL_POLL_KEY), "");
        //使用 rightPopAndLeftPush方式
//        String email = (String) RedisUtil.rightPopAndLeftPush(SystemConstant.EMAIL_POLL_KEY);
        return email;
    }


    /**
     * 通过邮箱使用模式获得一个邮箱对象
     * 内部调用
     *
     * @param config
     * @param list
     * @return
     */
    private static SysEmailConfigVo.Email getOneEmailByMode(SysEmailConfigVo.Config config, List<SysEmailConfigVo.Email> list) {
        String email;
        if (SystemConstant.EMAIL_MODE_POLL.equals(config.getMode())) {
            //邮箱使用模式_轮询
            email = rightPopAndLeftPushEmail();
        } else if (SystemConstant.EMAIL_MODE_RANDOM.equals(config.getMode())) {
            //邮箱使用模式_完全随机
            List<String> emails = MyUtil.joinToList(list, SysEmailConfigVo.Email::getEmail);
            email = RandomUtil.randomEle(emails);
        } else if (SystemConstant.EMAIL_MODE_RANDOM_WEIGHT.equals(config.getMode())) {
            //邮箱使用模式_权重随机
            List<WeightRandom.WeightObj<String>> weightObjs = new ArrayList<>();
            for (SysEmailConfigVo.Email email1 : list) {
                weightObjs.add(new WeightRandom.WeightObj<>(email1.getEmail(), Double.valueOf(email1.getWeight())));
            }
            email = RandomUtil.weightRandom(weightObjs).next();
        } else {
            email = null;
        }
        if (email == null) {
            return null;
        }
        List<SysEmailConfigVo.Email> emailList = list.stream().filter(item -> email.equals(item.getEmail())).collect(Collectors.toList());
        if (emailList.size() > 0) {
            return emailList.get(0);
        }
        return null;
    }


}
