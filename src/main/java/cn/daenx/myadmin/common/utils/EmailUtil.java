package cn.daenx.myadmin.common.utils;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysConfig;
import cn.daenx.myadmin.system.vo.SysEmailConfigVo;
import cn.hutool.core.util.ObjectUtil;
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


    public static String test() {
        List<String> list = new ArrayList<>();
        Object execute = RedisUtil.getRedisTemplate().execute(nextEmailScript, list, "1", "11");
        String msg = (String) execute;
        return msg;
    }


    /**
     * 获得一个邮箱配置
     *
     * @return
     */
    private SysEmailConfigVo.Email getConfig() {

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
        List<SysEmailConfigVo.Email> list = sysEmailConfigVo.getEmails().stream().filter(item -> "true".equals(item.getEnable())).collect(Collectors.toList());
        if (list.size() == 1) {
            return sysEmailConfigVo.getEmails().get(0);
        }
        //开始算法

        return null;
    }


}
