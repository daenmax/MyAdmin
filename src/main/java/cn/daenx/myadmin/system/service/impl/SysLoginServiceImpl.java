package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.Constant;
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
import cn.daenx.myadmin.system.vo.system.*;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @Resource
    private CaptchaService captchaService;


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
        captchaService.validatedCaptcha(vo);
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
            //校验账户状态
            sysUserService.validatedUser(sysUser);
            String sha256 = SaSecureUtil.sha256(vo.getPassword());
            if (!sha256.equals(sysUser.getPassword())) {
                SysLoginFailInfoVo sysLoginFailInfoVo = sysConfigService.getSysLoginFailInfoVo();
                String msg = "密码错误";
                if (sysLoginFailInfoVo != null) {
                    Integer failCount = 0;
                    Object value = RedisUtil.getValue(RedisConstant.LOGIN_FAIL + sysUser.getId());
                    if (value != null) {
                        failCount = (Integer) value;
                    }
                    failCount = failCount + 1;
                    if (failCount < sysLoginFailInfoVo.getFailCount()) {
                        //记录登录错误+1
                        RedisUtil.setValue(RedisConstant.LOGIN_FAIL + sysUser.getId(), failCount);
                        msg = "密码错误，您还可以尝试" + (sysLoginFailInfoVo.getFailCount() - failCount) + "次";
                    } else {
                        //封禁
                        LocalDateTime banToTime = LocalDateTime.now().plusSeconds(sysLoginFailInfoVo.getBanSecond());
                        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
                        wrapper.eq(SysUser::getId, sysUser.getId());
                        wrapper.set(SysUser::getBanToTime, banToTime);
                        sysUserService.update(wrapper);
                        RedisUtil.del(RedisConstant.LOGIN_FAIL + sysUser.getId());
                        String banToTimeStr = LocalDateTimeUtil.format(banToTime, Constant.DATE_TIME_FORMAT);
                        msg = "密码连续输入错误" + sysLoginFailInfoVo.getFailCount() + "次，账号被锁定，请于" + banToTimeStr + "后再试";
                    }
                }
                //记录登录日志
                sysLogLoginService.saveLogin(sysUser.getId(), sysUser.getUsername(), SystemConstant.LOGIN_FAIL, remark, clientIP, userAgent);
                throw new MyException(msg);
            }
            RedisUtil.del(RedisConstant.LOGIN_FAIL + sysUser.getId());
            SysLoginUserVo loginUserVo = new SysLoginUserVo();
            loginUserVo.setId(sysUser.getId());
            loginUserVo.setDeptId(sysUser.getDeptId());
            loginUserVo.setUsername(sysUser.getUsername());
            loginUserVo.setUserType(sysUser.getUserType());
            loginUserVo.setEmail(sysUser.getEmail());
            loginUserVo.setPhone(sysUser.getPhone());
            loginUserVo.setOpenId(sysUser.getOpenId());
            loginUserVo.setRoles(sysRoleService.getSysRoleListByUserId(sysUser.getId()));
            if (loginUserVo.getRoles().size() < 1) {
                //没有角色？肯定不行，最少一个才行，这种情况一般不会存在
                throw new MyException("用户无可用角色");
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
        captchaService.validatedCaptcha(vo);
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
