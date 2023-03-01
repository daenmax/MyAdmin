package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.enums.DeviceType;
import cn.daenx.myadmin.common.enums.LoginType;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.LoginUtil;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.common.utils.ServletUtils;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.service.*;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.daenx.myadmin.system.vo.SysLoginVo;
import cn.daenx.myadmin.system.vo.SysRegisterVo;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;


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
    @Resource
    private SysLogLoginService sysLogLoginService;

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
        String clientIP = ServletUtils.getClientIP();
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
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
                //记录登录日志
                sysLogLoginService.saveLogin(sysUser.getId(), sysUser.getUsername(), SystemConstant.LOGIN_FAIL, "PC登录，账号密码", clientIP, userAgent);
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
            //设置登录状态
            LoginUtil.login(loginUserVo, DeviceType.PC);
            //记录登录日志
            sysLogLoginService.saveLogin(sysUser.getId(), sysUser.getUsername(), SystemConstant.LOGIN_SUCCESS, "PC登录，账号密码", clientIP, userAgent);
            return StpUtil.getTokenValue();
        }
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
        //TODO 查询默认用户类型user_type_id
        String userTypeId = "2";
        //TODO 查询默认部门ID
        String deptId = "105";
        //TODO 查询默认角色ID
        String roleId = "2";
        //查询账号是否已存在
        SysUser sysUser = sysUserService.getUserByUsername(vo.getUsername());
        if (ObjectUtil.isNotEmpty(sysUser)) {
            throw new MyException("账号已存在");
        }
        String sha256 = SaSecureUtil.sha256(vo.getPassword());
        sysUser = new SysUser();
        sysUser.setDeptId(deptId);
        sysUser.setUsername(vo.getUsername());
        sysUser.setPassword(sha256);
        sysUser.setStatus(SystemConstant.USER_STATUS_NORMAL);
        sysUser.setUserTypeId(userTypeId);
        sysUserService.registerUser(sysUser, roleId);
    }
}
