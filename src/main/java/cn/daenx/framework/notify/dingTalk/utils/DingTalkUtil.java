package cn.daenx.framework.notify.dingTalk.utils;

import cn.daenx.framework.cache.utils.CacheUtil;
import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.domain.vo.system.config.SysConfigVo;
import cn.daenx.framework.common.domain.vo.system.config.SysDingTalkConfigVo;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.notify.dingTalk.domain.DingTalkSendResult;
import cn.daenx.framework.notify.dingTalk.service.DingTalkService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 钉钉群通知工具类
 *
 * @author DaenMax
 */
@Component
@Slf4j
public class DingTalkUtil {

    /**
     * 发送钉钉群通知
     * text类型，如果需要其他消息类型，请自己组装报文，然后调用sendByContent方法
     *
     * @param botName 机器人名称，在系统参数里自己填的，多个用,隔开
     * @param msg     消息内容，不需要自己写关键词
     * @return
     */
    public static List<DingTalkSendResult> sendMsg(String botName, String msg) {
        log.info("发送钉钉头部开始ing，botName={}，msg：{}", botName, msg);
        List<SysDingTalkConfigVo> configVo = getConfigVo(botName);
        if (CollUtil.isEmpty(configVo)) {
            log.info("发送钉钉{}，botName={}，msg：{}", false ? "成功" : "失败", botName, msg);
            throw new MyException("未找到对应配置");
        }
        List<DingTalkSendResult> list = new ArrayList<>();
        DingTalkService service;
        try {
            service = SpringUtil.getApplicationContext().getBean("dingTalk", DingTalkService.class);
        } catch (NoSuchBeanDefinitionException e) {
            log.info("发送钉钉{}，botName={}，content：{}，接口实现类未找到", false ? "成功" : "失败", botName, msg);
            throw new MyException("接口实现类未找到");
        }
        for (SysDingTalkConfigVo vo : configVo) {
            log.info("发送钉钉ing，botName={}，msg：{}", false ? "成功" : "失败", vo.getBotName(), msg);
            JSONObject req = new JSONObject();
            req.put("msgtype", "text");
            JSONObject text = new JSONObject();
            text.put("content", ObjectUtil.isEmpty(vo.getKeywords()) ? msg : vo.getKeywords() + msg);
            req.put("text", text);
            DingTalkSendResult result = service.sendMsg(vo, req.toJSONString());
            result.setBotName(botName);
            list.add(result);
            log.info("发送钉钉{}，botName={}，msg：{}，原因", result.isSuccess() ? "成功" : "失败", vo.getBotName(), msg, result.getMsg());
        }
        return list;
    }

    /**
     * 发送钉钉群通知
     * 自己组装报文，以便实现更多消息类型
     *
     * @param botName 机器人名称，在系统参数里自己填的，多个用,隔开
     * @param content JSON格式的数据，参考钉钉官网文档，不需要自己计算签名，但是需要写关键词（如果有的话）
     * @return
     */
    public static List<DingTalkSendResult> sendByContent(String botName, String content) {
        log.info("发送钉钉头部开始ing，botName={}，content：{}", botName, content);
        List<SysDingTalkConfigVo> configVo = getConfigVo(botName);
        if (CollUtil.isEmpty(configVo)) {
            log.info("发送钉钉{}，botName={}，content：{}", false ? "成功" : "失败", botName, content);
            throw new MyException("未找到对应配置");
        }
        List<DingTalkSendResult> list = new ArrayList<>();
        DingTalkService service;
        try {
            service = SpringUtil.getApplicationContext().getBean("dingTalk", DingTalkService.class);
        } catch (NoSuchBeanDefinitionException e) {
            log.info("发送钉钉{}，botName={}，content：{}，接口实现类未找到", false ? "成功" : "失败", botName, content);
            throw new MyException("接口实现类未找到");
        }
        for (SysDingTalkConfigVo vo : configVo) {
            log.info("发送钉钉ing，botName={}，content：{}", false ? "成功" : "失败", vo.getBotName(), content);
            DingTalkSendResult result = service.sendMsg(vo, content);
            result.setBotName(botName);
            list.add(result);
            log.info("发送钉钉{}，botName={}，content：{}，原因", result.isSuccess() ? "成功" : "失败", vo.getBotName(), content, result.getMsg());
        }
        return list;
    }


    /**
     * 从redis里获取系统钉钉配置
     * 不存在或者被禁用或者数量为0返回null
     *
     * @param botName 机器人名称，在系统参数里自己填的，多个用,隔开
     * @return
     */
    private static List<SysDingTalkConfigVo> getConfigVo(String botName) {
        Object object = CacheUtil.getValue(RedisConstant.CONFIG + "sys.dingTalk.config");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfigVo sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfigVo.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return null;
        }
        List<SysDingTalkConfigVo> list = new ArrayList<>();
        Set<String> set = Arrays.stream(botName.split(",")).collect(Collectors.toSet());
        String[] array = ArrayUtil.toArray(set, String.class);
        for (String name : array) {
            JSONObject jsonObject = JSONObject.parseObject(sysConfig.getValue());
            SysDingTalkConfigVo vo = jsonObject.getObject(name, SysDingTalkConfigVo.class);
            if (ObjectUtil.isNotEmpty(vo)) {
                vo.setBotName(name);
                list.add(vo);
            }
        }
        return list;
    }

}
