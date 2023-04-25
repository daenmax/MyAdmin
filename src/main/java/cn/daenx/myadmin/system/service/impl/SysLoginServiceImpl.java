package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.enums.DeviceType;
import cn.daenx.myadmin.common.enums.LoginType;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.common.utils.ServletUtils;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.dto.SysUserPageDto;
import cn.daenx.myadmin.system.po.*;
import cn.daenx.myadmin.system.service.*;
import cn.daenx.myadmin.system.vo.*;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class SysLoginServiceImpl implements SysLoginService {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysLogLoginService sysLogLoginService;
    @Resource
    private SysPositionService sysPositionService;
    @Resource
    private LoginUtilService loginUtilService;
    @Resource
    private SysConfigService sysConfigService;

    /**
     * 校验图片验证码
     *
     * @param code
     * @param uuid
     */
    private void validatedCaptchaImg(String code, String uuid) {
        Boolean lockCaptchaImg = Boolean.parseBoolean(sysConfigService.getConfigByKey("sys.lock.captchaImg"));
        if (lockCaptchaImg) {
            if (ObjectUtil.isEmpty(code) || ObjectUtil.isEmpty(uuid)) {
                throw new MyException("验证码相关参数不能为空");
            }
            String codeReal = (String) RedisUtil.getValue(RedisConstant.CAPTCHA_IMG + uuid);
            if (ObjectUtil.isEmpty(codeReal)) {
                throw new MyException("验证码已过期，请刷新验证码");
            }
            if (!codeReal.equals(code)) {
                RedisUtil.del(RedisConstant.CAPTCHA_IMG + uuid);
                throw new MyException("验证码错误");
            }
            RedisUtil.del(RedisConstant.CAPTCHA_IMG + uuid);
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
        String remark = "PC登录";
        String clientIP = ServletUtils.getClientIP();
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        //校验验证码
        validatedCaptchaImg(vo.getCode(), vo.getUuid());
        if (vo.getLoginType().equals(LoginType.ACCOUNT.getCode())) {
            remark = remark + "/" + LoginType.ACCOUNT.getDesc();
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
                sysLogLoginService.saveLogin(sysUser.getId(), sysUser.getUsername(), SystemConstant.LOGIN_FAIL, remark, clientIP, userAgent);
                throw new MyException("密码错误");
            }
            //校验账户状态
            sysUserService.validatedUser(sysUser);
            SysLoginUserVo loginUserVo = new SysLoginUserVo();
            loginUserVo.setId(sysUser.getId());
            loginUserVo.setDeptId(sysUser.getDeptId());
            loginUserVo.setUsername(sysUser.getUsername());
            loginUserVo.setUserType(sysUser.getUserType());
            loginUserVo.setRoles(sysRoleService.getSysRoleListByUserId(sysUser.getId()));
            if (loginUserVo.getRoles().size() < 1) {
                //没有角色？肯定不行，最少一个才行，这种情况一般不会存在
                throw new MyException("用户角色");
            }
            Boolean isRoleOk = false;
            for (SysRole role : loginUserVo.getRoles()) {
                if (role.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
                    isRoleOk = true;
                    break;
                }
            }
            if (!isRoleOk) {
                //用户绑定的角色全部被禁用了
                throw new MyException("用户角色全部不可用");
            }
            loginUserVo.setPositions(sysPositionService.getSysPositionListByUserId(sysUser.getId()));
            if (loginUserVo.getPositions().size() > 0) {
                Boolean isPositionOk = false;
                for (SysPosition sysPosition : loginUserVo.getPositions()) {
                    if (sysPosition.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
                        isPositionOk = true;
                        break;
                    }
                }
                if (!isPositionOk) {
                    //用户绑定的岗位全部被禁用了
                    throw new MyException("用户岗位全部不可用");
                }
            }
            loginUserVo.setRolePermission(sysRoleService.getRolePermissionListByUserId(sysUser.getId()));
            loginUserVo.setMenuPermission(sysMenuService.getMenuPermissionByUser(loginUserVo));
            //设置登录状态
            loginUtilService.login(loginUserVo, DeviceType.PC);
            //记录登录日志
            sysLogLoginService.saveLogin(sysUser.getId(), sysUser.getUsername(), SystemConstant.LOGIN_SUCCESS, remark, clientIP, userAgent);
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
        //判空
        if (ObjectUtil.isEmpty(vo.getUsername()) || ObjectUtil.isEmpty(vo.getPassword())) {
            throw new MyException("账号和密码不能为空");
        }
        Boolean lockRegister = Boolean.parseBoolean(sysConfigService.getConfigByKey("sys.lock.register"));
        if (!lockRegister) {
            throw new MyException("系统未开放注册");
        }
        SysRegisterDefaultInfoVo sysRegisterDefaultInfoVo = sysConfigService.getSysRegisterDefaultInfoVo();
        if (ObjectUtil.isEmpty(sysRegisterDefaultInfoVo)) {
            throw new MyException("系统当前无法注册[0x1]");
        }
        if (ObjectUtil.isEmpty(sysRegisterDefaultInfoVo.getUserType())) {
            throw new MyException("系统当前无法注册[0x2]");
        }
        if (ObjectUtil.isEmpty(sysRegisterDefaultInfoVo.getDeptCode())) {
            throw new MyException("系统当前无法注册[0x2]");
        }
        if (ObjectUtil.isEmpty(sysRegisterDefaultInfoVo.getRoleCodes())) {
            throw new MyException("系统当前无法注册[0x3]");
        }
        if (sysRegisterDefaultInfoVo.getRoleCodes().length == 0) {
            throw new MyException("系统当前无法注册[0x3]");
        }
        SysDept sysDeptByCode = sysDeptService.getSysDeptByCode(sysRegisterDefaultInfoVo.getDeptCode());
        if (!sysDeptByCode.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
            throw new MyException("系统当前无法注册[0x4]");
        }
        //查询账号是否已存在
        SysUser sysUser = sysUserService.getUserByUsername(vo.getUsername());
        if (ObjectUtil.isNotEmpty(sysUser)) {
            throw new MyException("账号已存在");
        }
        String sha256 = SaSecureUtil.sha256(vo.getPassword());
        sysUser = new SysUser();
        sysUser.setDeptId(sysDeptByCode.getId());
        sysUser.setUsername(vo.getUsername());
        sysUser.setPassword(sha256);
        sysUser.setStatus(SystemConstant.USER_STATUS_NORMAL);
        sysUser.setUserType(sysRegisterDefaultInfoVo.getUserType());
        sysUserService.registerUser(sysUser, sysRegisterDefaultInfoVo.getRoleCodes(), sysRegisterDefaultInfoVo.getPositionCodes());
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @Override
    public Map<String, Object> getInfo() {
        SysLoginUserVo loginUser = loginUtilService.getLoginUser();
        SysUserPageDto sysUserVo = sysUserService.getUserInfoByUserId(loginUser.getId());
        if (sysUserVo == null) {
            throw new MyException("用户不存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user", sysUserVo);
        map.put("roles", loginUser.getRolePermission());
        map.put("permissions", loginUser.getMenuPermission());
        return map;
    }

    /**
     * 根据用户获取菜单树
     *
     * @return
     */
    @Override
    public List<RouterVo> getRouters() {
        String userId = loginUtilService.getLoginUserId();
        List<SysMenu> menuList = sysMenuService.getMenuTreeByUserId(userId);
        return sysMenuService.buildMenus(menuList);
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        loginUtilService.logout();
    }
}
