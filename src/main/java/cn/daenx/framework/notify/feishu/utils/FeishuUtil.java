package cn.daenx.framework.notify.feishu.utils;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.RedisUtil;
import cn.daenx.framework.common.vo.system.config.SysConfigVo;
import cn.daenx.framework.common.vo.system.config.SysFeishuConfigVo;
import cn.daenx.framework.notify.feishu.service.FeishuService;
import cn.daenx.framework.notify.feishu.vo.FeishuSendResult;
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
 * 飞书群通知工具类
 *
 * @author DaenMax
 */
@Component
@Slf4j
public class FeishuUtil {

    /**
     * 发送飞书群通知
     * text类型，如果需要其他消息类型，请自己组装报文，然后调用sendByContent方法
     *
     * @param botName 机器人名称，在系统参数里自己填的，多个用,隔开
     * @param msg     消息内容，不需要自己写关键词
     * @return
     */
    public static List<FeishuSendResult> sendMsg(String botName, String msg) {
        log.info("发送飞书头部开始ing，botName={}，msg：{}", botName, msg);
        List<SysFeishuConfigVo> configVoList = getConfigVo(botName);
        if (CollUtil.isEmpty(configVoList)) {
            log.info("发送飞书{}，botName={}，msg：{}", false ? "成功" : "失败", botName, msg);
            throw new MyException("未找到对应配置");
        }
        List<FeishuSendResult> list = new ArrayList<>();
        FeishuService service;
        try {
            service = SpringUtil.getApplicationContext().getBean("feishu", FeishuService.class);
        } catch (NoSuchBeanDefinitionException e) {
            log.info("发送飞书{}，botName={}，content：{}，接口实现类未找到", false ? "成功" : "失败", botName, msg);
            throw new MyException("接口实现类未找到");
        }
        for (SysFeishuConfigVo vo : configVoList) {
            log.info("发送飞书ing，botName={}，msg：{}", false ? "成功" : "失败", vo.getBotName(), msg);
            JSONObject req = new JSONObject();
            req.put("msg_type", "text");
            JSONObject text = new JSONObject();
            text.put("text", ObjectUtil.isEmpty(vo.getKeywords()) ? msg : vo.getKeywords() + msg);
            req.put("content", text);
            FeishuSendResult result = service.sendMsg(vo, req.toJSONString());
            result.setBotName(botName);
            list.add(result);
            log.info("发送飞书{}，botName={}，msg：{}，原因", result.isSuccess() ? "成功" : "失败", vo.getBotName(), msg, result.getMsg());
        }
        return list;
    }

    /**
     * 发送飞书群通知
     * 自己组装报文，以便实现更多消息类型
     *
     * @param botName 机器人名称，在系统参数里自己填的，多个用,隔开
     * @param content JSON格式的数据，参考飞书官网文档，不需要自己计算签名，但是需要写关键词（如果有的话）
     * @return
     */
    public static List<FeishuSendResult> sendByContent(String botName, String content) {
        log.info("发送飞书头部开始ing，botName={}，content：{}", botName, content);
        List<SysFeishuConfigVo> configVoList = getConfigVo(botName);
        if (CollUtil.isEmpty(configVoList)) {
            log.info("发送飞书{}，botName={}，content：{}", false ? "成功" : "失败", botName, content);
            throw new MyException("未找到对应配置");
        }
        List<FeishuSendResult> list = new ArrayList<>();
        FeishuService service;
        try {
            service = SpringUtil.getApplicationContext().getBean("feishu", FeishuService.class);
        } catch (NoSuchBeanDefinitionException e) {
            log.info("发送飞书{}，botName={}，content：{}，接口实现类未找到", false ? "成功" : "失败", botName, content);
            throw new MyException("接口实现类未找到");
        }
        for (SysFeishuConfigVo vo : configVoList) {
            log.info("发送飞书ing，botName={}，content：{}", false ? "成功" : "失败", vo.getBotName(), content);
            FeishuSendResult result = service.sendMsg(vo, content);
            result.setBotName(botName);
            list.add(result);
            log.info("发送飞书{}，botName={}，content：{}，原因", result.isSuccess() ? "成功" : "失败", vo.getBotName(), content, result.getMsg());
        }
        return list;
    }


    /**
     * 从redis里获取系统飞书配置
     * 不存在或者被禁用或者数量为0返回null
     *
     * @param botName 机器人名称，在系统参数里自己填的，多个用,隔开
     * @return
     */
    private static List<SysFeishuConfigVo> getConfigVo(String botName) {
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + "sys.feishu.config");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfigVo sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfigVo.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return null;
        }
        List<SysFeishuConfigVo> list = new ArrayList<>();
        Set<String> set = Arrays.stream(botName.split(",")).collect(Collectors.toSet());
        String[] array = ArrayUtil.toArray(set, String.class);
        for (String name : array) {
            JSONObject jsonObject = JSONObject.parseObject(sysConfig.getValue());
            SysFeishuConfigVo vo = jsonObject.getObject(name, SysFeishuConfigVo.class);
            if (ObjectUtil.isNotEmpty(vo)) {
                vo.setBotName(name);
                list.add(vo);
            }
        }
        return list;
    }

}
