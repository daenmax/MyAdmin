package cn.daenx.myadmin.common.utils;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysConfig;
import cn.daenx.myadmin.system.vo.SysEmailConfigVo;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
     * 从redis里获取系统邮箱配置信息
     * 不存在或者被禁用或者数量为0返回null
     *
     * @return
     */
    public static SysEmailConfigVo getSysEmailConfigVo() {
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
     * 邮箱使用模式_轮询时，弹出一个邮箱并重新放进去
     *
     * @return
     */
    public static String rightPopAndLeftPushEmail() {
        //TODO 使用redis lua方式，待完成
//        String email = (String) RedisUtil.getRedisTemplate().execute(nextEmailScript, null, "");
        //使用 rightPopAndLeftPush方式
        String email = (String) RedisUtil.rightPopAndLeftPush(SystemConstant.EMAIL_POLL_KEY);
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
