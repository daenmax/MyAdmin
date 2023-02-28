package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.enums.DeviceType;
import cn.daenx.myadmin.common.enums.LoginType;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.LoginUtil;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.common.utils.ServletUtils;
import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.service.*;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.daenx.myadmin.system.vo.SysLoginVo;
import cn.daenx.myadmin.system.vo.SysRegisterVo;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SysLoginServiceImpl implements SysLoginService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserTypeService sysUserTypeService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysMenuService sysMenuService;

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
     * PC登录
     *
     * @param vo
     * @return
     */
    @Override
    public String login(SysLoginVo vo) {
        //校验验证码
        validatedCaptchaImg(vo.getCode(), vo.getUuid());
        if (vo.getLoginType().equals(LoginType.ACCOUNT.getCode())) {
            //账号密码登录
            if (ObjectUtil.hasEmpty(vo.getUsername(), vo.getPassword())) {
                throw new MyException("账号和密码不能为空");
            }
            SysUser sysUser = sysUserService.getUserByUsername(vo.getUsername());
            if (ObjectUtil.isEmpty(sysUser)) {
                throw new MyException("账号不存在");
            }
            String sha256 = SaSecureUtil.sha256(vo.getPassword());
            if (!sha256.equals(sysUser.getPassword())) {
                throw new MyException("密码错误");
            }
            //校验账户状态
            sysUserService.validatedUser(sysUser);

            SysLoginUserVo loginUserVo = new SysLoginUserVo();
            loginUserVo.setId(sysUser.getId());
            loginUserVo.setUsername(sysUser.getUsername());
            loginUserVo.setUserType(sysUserTypeService.getSysUserTypeById(sysUser.getUserTypeId()));
            loginUserVo.setRoles(sysRoleService.getSysRoleListByUserId(sysUser.getId()));
            loginUserVo.setRolePermission(sysRoleService.getRolePermissionListByUserId(sysUser.getId()));
            loginUserVo.setMenuPermission(sysMenuService.getMenuPermissionByUser(loginUserVo));
            LoginUtil.login(loginUserVo, DeviceType.PC);
            String tokenValue = StpUtil.getTokenValue();
            //更新最后登录信息
            sysUserService.updateUserLogin(sysUser.getId(), ServletUtils.getClientIP());
        }

        SysUser sysUser = new SysUser();
        sysUser.setId("123");
        SysLoginUserVo loginUserVo = new SysLoginUserVo();
        System.out.println(StpUtil.getLoginId());
        System.out.println(StpUtil.getLoginDevice());
        //密码校验用不可逆的算法

        return "";
    }

    /**
     * 通用注册接口
     * 只接受账号和密码
     * 手机号、邮箱、openid需要另外单独绑定
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
