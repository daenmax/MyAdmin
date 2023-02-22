package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.enums.DeviceType;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.service.SysLoginService;
import cn.daenx.myadmin.system.vo.SysLoginVo;
import cn.daenx.myadmin.system.vo.SysRegisterVo;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SysLoginServiceImpl implements SysLoginService {
    @Resource
    private RedisUtil redisUtil;

    /**
     * 用户登录
     *
     * @param vo
     * @return
     */
    @Override
    public String login(SysLoginVo vo) {
        //校验验证码，读取系统参数
        if (1 == 1) {
            if (ObjectUtil.isEmpty(vo.getCode()) || ObjectUtil.isEmpty(vo.getUuid())) {
                throw new MyException("验证码相关参数不能为空");
            }
            String code = (String) redisUtil.get(RedisConstant.CAPTCHA_IMG + vo.getUuid());
            if (ObjectUtil.isEmpty(code)) {
                throw new MyException("验证码已过期，请刷新验证码");
            }
            if (!code.equals(vo.getCode())){
                redisUtil.del(RedisConstant.CAPTCHA_IMG + vo.getUuid());
                throw new MyException("验证码错误");
            }
            redisUtil.del(RedisConstant.CAPTCHA_IMG + vo.getUuid());
        }
        SysUser sysUser = new SysUser();
        sysUser.setId("123");
        SaHolder.getStorage().set("loginuser",sysUser);
        StpUtil.login(vo.getUsername(), DeviceType.PC.getCode());
        StpUtil.getTokenSession().set("loginuser", sysUser);
        System.out.println(StpUtil.getLoginId());
        System.out.println(StpUtil.getLoginDevice());
        return StpUtil.getTokenValue();
    }

    /**
     * 用户注册
     *
     * @param vo
     */
    @Override
    public void register(SysRegisterVo vo) {
        //TODO 检查系统是否开启了注册功能

    }
}
