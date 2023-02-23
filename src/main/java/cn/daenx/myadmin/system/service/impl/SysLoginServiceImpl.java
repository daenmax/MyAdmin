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
     * 校验图片验证码
     *
     * @param code
     * @param uuid
     */
    private void validatedCaptchaImg(String code, String uuid) {
        //TODO 校验验证码，读取系统参数
        if (1 == 1) {
            if (ObjectUtil.isEmpty(code) || ObjectUtil.isEmpty(uuid)) {
                throw new MyException("验证码相关参数不能为空");
            }
            String codeReal = (String) redisUtil.get(RedisConstant.CAPTCHA_IMG + uuid);
            if (ObjectUtil.isEmpty(codeReal)) {
                throw new MyException("验证码已过期，请刷新验证码");
            }
            if (!codeReal.equals(code)) {
                redisUtil.del(RedisConstant.CAPTCHA_IMG + uuid);
                throw new MyException("验证码错误");
            }
            redisUtil.del(RedisConstant.CAPTCHA_IMG + uuid);
        }
    }

    /**
     * 用户登录
     *
     * @param vo
     * @return
     */
    @Override
    public String login(SysLoginVo vo) {
        validatedCaptchaImg(vo.getCode(), vo.getUuid());
        SysUser sysUser = new SysUser();
        sysUser.setId("123");
        SaHolder.getStorage().set("loginuser", sysUser);
        StpUtil.login(vo.getUsername(), DeviceType.PC.getCode());
        StpUtil.getTokenSession().set("loginuser", sysUser);
        System.out.println(StpUtil.getLoginId());
        System.out.println(StpUtil.getLoginDevice());
        //密码校验用不可逆的算法

        return StpUtil.getTokenValue();
    }

    /**
     * 用户注册
     *
     * @param vo
     */
    @Override
    public void register(SysRegisterVo vo) {
        validatedCaptchaImg(vo.getCode(), vo.getUuid());
        //TODO 检查系统是否开启了注册功能
        //判空
        if (ObjectUtil.isEmpty(vo.getUsername()) || ObjectUtil.isEmpty(vo.getPassword())) {
            throw new MyException("账号和密码不能为空");
        }
        //查询账号是否已存在
    }
}
