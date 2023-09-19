package cn.daenx.framework.notify.dingTalk.utils;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.utils.RedisUtil;
import cn.daenx.framework.common.vo.system.utils.DingTalkSendResult;
import cn.daenx.framework.common.vo.system.config.SysConfigVo;
import cn.daenx.framework.common.vo.system.config.SysDingTalkConfigVo;
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
     * text类型，如果需要其他消息类型，请自己组装报文，然后调用DingTalkSendResult方法
     *
     * @param botName 机器人名称，在系统参数里自己填的，多个用,隔开
     * @param msg     消息内容
     * @return
     */
    public static List<DingTalkSendResult> sendTalk(String botName, String msg) {
        log.info("发送钉钉头部开始ing，botName={}，msg：{}", botName, msg);
        List<SysDingTalkConfigVo> sysSmsConfigVoList = getSysSmsConfigVo(botName);
        if (CollUtil.isEmpty(sysSmsConfigVoList)) {
            log.info("发送钉钉{}，botName={}，msg：{}", false ? "成功" : "失败", botName, msg);
            return null;
        }
        List<DingTalkSendResult> list = new ArrayList<>();
        DingTalkService dingTalkService;
        try {
            dingTalkService = SpringUtil.getApplicationContext().getBean("dingTalk", DingTalkService.class);
        } catch (NoSuchBeanDefinitionException e) {
            log.info("发送钉钉{}，botName={}，content：{}，接口实现类未找到", false ? "成功" : "失败", botName, msg);
            return null;
        }
        for (SysDingTalkConfigVo sysSmsConfigVo : sysSmsConfigVoList) {
            log.info("发送钉钉ing，botName={}，msg：{}", false ? "成功" : "失败", sysSmsConfigVo.getBotName(), msg);
            JSONObject req = new JSONObject();
            req.put("msgtype", "text");
            JSONObject text = new JSONObject();
            text.put("content", ObjectUtil.isEmpty(sysSmsConfigVo.getKeywords()) ? msg : sysSmsConfigVo.getKeywords() + msg);
            req.put("text", text);
            DingTalkSendResult dingTalkSendResult = dingTalkService.sendMsg(sysSmsConfigVo, req.toJSONString());
            dingTalkSendResult.setBotName(botName);
            list.add(dingTalkSendResult);
            log.info("发送钉钉{}，botName={}，msg：{}，原因", dingTalkSendResult.isSuccess() ? "成功" : "失败", sysSmsConfigVo.getBotName(), msg, dingTalkSendResult.getMsg());
        }
        return list;
    }

    /**
     * 发送钉钉群通知
     * 自己组装报文，以便实现更多消息类型
     *
     * @param botName 机器人名称，在系统参数里自己填的，多个用,隔开
     * @param content JSON格式的数据
     * @return
     */
    public static List<DingTalkSendResult> sendTalkContent(String botName, String content) {
        log.info("发送钉钉头部开始ing，botName={}，content：{}", botName, content);
        List<SysDingTalkConfigVo> sysSmsConfigVoList = getSysSmsConfigVo(botName);
        if (CollUtil.isEmpty(sysSmsConfigVoList)) {
            log.info("发送钉钉{}，botName={}，content：{}", false ? "成功" : "失败", botName, content);
            return null;
        }
        List<DingTalkSendResult> list = new ArrayList<>();
        DingTalkService dingTalkService;
        try {
            dingTalkService = SpringUtil.getApplicationContext().getBean("dingTalk", DingTalkService.class);
        } catch (NoSuchBeanDefinitionException e) {
            log.info("发送钉钉{}，botName={}，content：{}，接口实现类未找到", false ? "成功" : "失败", botName, content);
            return null;
        }
        for (SysDingTalkConfigVo sysSmsConfigVo : sysSmsConfigVoList) {
            log.info("发送钉钉ing，botName={}，content：{}", false ? "成功" : "失败", sysSmsConfigVo.getBotName(), content);
            DingTalkSendResult dingTalkSendResult = dingTalkService.sendMsg(sysSmsConfigVo, content);
            dingTalkSendResult.setBotName(botName);
            list.add(dingTalkSendResult);
            log.info("发送钉钉{}，botName={}，content：{}，原因", dingTalkSendResult.isSuccess() ? "成功" : "失败", sysSmsConfigVo.getBotName(), content, dingTalkSendResult.getMsg());
        }
        return list;
    }


    /**
     * 从redis里获取系统钉钉通知配置
     * 不存在或者被禁用或者数量为0返回null
     *
     * @param botName 机器人名称，在系统参数里自己填的，多个用,隔开
     * @return
     */
    private static List<SysDingTalkConfigVo> getSysSmsConfigVo(String botName) {
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + "sys.dingTalk.config");
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
            SysDingTalkConfigVo sysDingTalkConfigVo = jsonObject.getObject(name, SysDingTalkConfigVo.class);
            if (ObjectUtil.isNotEmpty(sysDingTalkConfigVo)) {
                sysDingTalkConfigVo.setBotName(name);
                list.add(sysDingTalkConfigVo);
            }
        }
        return list;
    }

}
