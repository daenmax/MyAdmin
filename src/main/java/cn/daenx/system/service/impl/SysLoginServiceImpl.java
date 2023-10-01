package cn.daenx.system.service.impl;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.constant.enums.DeviceType;
import cn.daenx.framework.common.constant.enums.LoginType;
import cn.daenx.framework.common.utils.*;
import cn.daenx.framework.common.vo.RouterVo;
import cn.daenx.framework.common.vo.system.config.SysSendLimitConfigVo;
import cn.daenx.framework.common.vo.system.config.SysSmsTemplateConfigVo;
import cn.daenx.framework.common.vo.system.other.SysLoginUserVo;
import cn.daenx.framework.common.vo.system.other.SysPositionVo;
import cn.daenx.framework.common.vo.system.other.SysRoleVo;
import cn.daenx.framework.notify.sms.vo.SmsSendResult;
import cn.daenx.framework.notify.email.utils.EmailUtil;
import cn.daenx.framework.notify.sms.utils.SmsUtil;
import cn.daenx.framework.satoken.utils.LoginUtil;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.CheckSendVo;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.system.domain.dto.SysUserPageDto;
import cn.daenx.system.domain.po.*;
import cn.daenx.system.domain.vo.SysLoginFailInfoVo;
import cn.daenx.system.domain.vo.SysLoginVo;
import cn.daenx.system.domain.vo.SysRegisterDefaultInfoVo;
import cn.daenx.system.domain.vo.SysRegisterVo;
import cn.daenx.system.service.*;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


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
    private SysConfigService sysConfigService;
    @Resource
    private CaptchaService captchaService;

    @Value("${system-info.name}")
    private String systemInfoName;

    /**
     * 获取邮箱验证码
     *
     * @param vo
     * @return
     */
    @Override
    public Result getEmailValidCode(SysLoginVo vo) {
        captchaService.validatedCaptcha(vo);
        if (ObjectUtil.isEmpty(vo.getEmail())) {
            throw new MyException("请填写邮箱");
        }
        SysUser sysUser = sysUserService.getUserByEmail(vo.getEmail());
        if (ObjectUtil.isEmpty(sysUser)) {
            throw new MyException("邮箱不存在");
        }
        //校验账户状态
        sysUserService.validatedUser(sysUser);
        SysSendLimitConfigVo sysSendLimitConfigVo = sysConfigService.getSysSendLimitConfigVo();
        Long keepLive = 0L;
        if (sysSendLimitConfigVo != null) {
            keepLive = Long.valueOf(sysSendLimitConfigVo.getEmail().getKeepLive());
        }
        //例如：5分钟
        String keepLiveStr = MyUtil.timeDistance(keepLive * 1000);
        String value = (String) RedisUtil.getValue(RedisConstant.LOGIN_EMAIL + sysUser.getId() + ":" + vo.getEmail());
        if (ObjectUtil.isNotEmpty(value)) {
            throw new MyException("验证码尚未失效，如未收到验证码请" + keepLiveStr + "后再试");
        }
        CheckSendVo checkSendVo = EmailUtil.checkSendByUserId(sysUser.getId(), sysSendLimitConfigVo);
        if (!checkSendVo.getNowOk()) {
            throw new MyException(checkSendVo.getMsg());
        }
        //生成验证码
        String code = RandomUtil.randomNumbers(6);
        String subject = "【" + systemInfoName + "】" + "邮件验证";
        String content = MyUtil.buildCodeValida(systemInfoName, code);
        Boolean aBoolean = EmailUtil.sendEmail(vo.getEmail(), subject, content, true, null);
        if (!aBoolean) {
            throw new MyException("发送邮件失败，请联系管理员");
        }
        //有效期30分钟
        RedisUtil.setValue(RedisConstant.LOGIN_EMAIL + sysUser.getId() + ":" + vo.getEmail(), code, keepLive, TimeUnit.SECONDS);
        Integer waitTime = EmailUtil.saveSendByUserId(sysUser.getId(), sysSendLimitConfigVo);
        Map<String, Object> map = new HashMap<>();
        map.put("waitTime", waitTime);
        map.put("msg", "验证码已发送，" + keepLiveStr + "有效");
        //这里设置一层验证码UUID缓存，为后面点击登录增加一道障碍，避免出现不输入验证码就一直点登录，对面通过遍历造成撞库泄露风险
        RedisUtil.setValue(RedisConstant.CACHE_UUID + vo.getUuid(), vo.getUuid(), keepLive, TimeUnit.SECONDS);
        return Result.ok(map);
    }

    /**
     * 获取手机验证码
     *
     * @param vo
     * @return
     */
    @Override
    public Result getPhoneValidCode(SysLoginVo vo) {
        captchaService.validatedCaptcha(vo);
        if (ObjectUtil.isEmpty(vo.getPhone())) {
            throw new MyException("请填写邮箱");
        }
        SysUser sysUser = sysUserService.getUserByPhone(vo.getPhone());
        if (ObjectUtil.isEmpty(sysUser)) {
            throw new MyException("手机号不存在");
        }
        //校验账户状态
        sysUserService.validatedUser(sysUser);
        SysSendLimitConfigVo sysSendLimitConfigVo = sysConfigService.getSysSendLimitConfigVo();
        Long keepLive = 0L;
        if (sysSendLimitConfigVo != null) {
            keepLive = Long.valueOf(sysSendLimitConfigVo.getEmail().getKeepLive());
        }
        //例如：5分钟
        String keepLiveStr = MyUtil.timeDistance(keepLive * 1000);
        String value = (String) RedisUtil.getValue(RedisConstant.LOGIN_PHONE + sysUser.getId() + ":" + vo.getPhone());
        if (ObjectUtil.isNotEmpty(value)) {
            throw new MyException("验证码尚未失效，如未收到验证码请" + keepLiveStr + "后再试");
        }
        CheckSendVo checkSendVo = SmsUtil.checkSendByUserId(sysUser.getId(), sysSendLimitConfigVo);
        if (!checkSendVo.getNowOk()) {
            throw new MyException(checkSendVo.getMsg());
        }
        SysSmsTemplateConfigVo sysSmsTemplateConfigVo = sysConfigService.getSysSmsTemplateConfigVo();
        if (ObjectUtil.isEmpty(sysSmsTemplateConfigVo)) {
            throw new MyException("没有配置短信模板参数，请联系管理员");
        }
        if (ObjectUtil.isEmpty(sysSmsTemplateConfigVo.getLogin())) {
            throw new MyException("没有配置login短信模板参数，请联系管理员");
        }
        //生成验证码
        String code = RandomUtil.randomNumbers(6);
        Map<String, String> smsMap = new HashMap<>();
        smsMap.put(sysSmsTemplateConfigVo.getBindPhone().getVariable(), code);
        SmsSendResult smsSendResult = SmsUtil.sendSms(vo.getPhone(), sysSmsTemplateConfigVo.getBindPhone().getTemplateId(), smsMap);
        if (!smsSendResult.isSuccess()) {
            throw new MyException("发送短信失败，请联系管理员");
        }
        RedisUtil.setValue(RedisConstant.LOGIN_PHONE + sysUser.getId() + ":" + vo.getPhone(), code, keepLive, TimeUnit.SECONDS);
        Integer waitTime = SmsUtil.saveSendByUserId(sysUser.getId(), sysSendLimitConfigVo);
        Map<String, Object> map = new HashMap<>();
        map.put("waitTime", waitTime);
        map.put("msg", "验证码已发送，" + keepLiveStr + "有效");
        //这里设置一层验证码UUID缓存，为后面点击登录增加一道障碍，避免出现不输入验证码就一直点登录，对面通过遍历造成撞库泄露风险
        RedisUtil.setValue(RedisConstant.CACHE_UUID + vo.getUuid(), vo.getUuid(), keepLive, TimeUnit.SECONDS);
        return Result.ok(map);
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
        SysUser sysUser = null;
        if (vo.getLoginType().equals(LoginType.USERNAME.getCode())) {
            //账号密码登录
            //校验验证码
            captchaService.validatedCaptcha(vo);
            remark = remark + "/" + LoginType.USERNAME.getDesc();
            if (ObjectUtil.hasEmpty(vo.getUsername(), vo.getPassword())) {
                throw new MyException("账号和密码不能为空");
            }
            //判断用户输入的账号是 账号还是邮箱还是手机号
            Integer usernameType = MyUtil.getUsernameType(vo.getUsername());
            if (usernameType == 1) {
                sysUser = sysUserService.getUserByUsername(vo.getUsername());
            } else if (usernameType == 2) {
                sysUser = sysUserService.getUserByEmail(vo.getUsername());
            } else if (usernameType == 3) {
                sysUser = sysUserService.getUserByPhone(vo.getUsername());
                if (sysUser == null) {
                    //某些情况下，账号会设置成手机号，这个时候，需要进行二次判断
                    sysUser = sysUserService.getUserByUsername(vo.getUsername());
                }
            }
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
                        String banToTimeStr = LocalDateTimeUtil.format(banToTime, CommonConstant.DATE_TIME_FORMAT);
                        msg = "密码连续输入错误" + sysLoginFailInfoVo.getFailCount() + "次，账号被锁定，请于" + banToTimeStr + "后再试";
                    }
                }
                //记录登录日志
                sysLogLoginService.saveLogin(sysUser.getId(), sysUser.getUsername(), SystemConstant.LOGIN_FAIL, remark, clientIP, userAgent);
                throw new MyException(msg);
            }
        } else if (vo.getLoginType().equals(LoginType.EMAIL.getCode())) {
            //邮箱验证码登录
            remark = remark + "/" + LoginType.EMAIL.getDesc();
            if (ObjectUtil.hasEmpty(vo.getEmail())) {
                throw new MyException("邮箱不能为空");
            }
            if (ObjectUtil.hasEmpty(vo.getValidCode())) {
                throw new MyException("邮箱验证码不能为空");
            }
            Object value = RedisUtil.getValue(RedisConstant.CACHE_UUID + vo.getUuid());
            if (ObjectUtil.isEmpty(value)) {
                throw new MyException("请求不合法");
            }
            sysUser = sysUserService.getUserByEmail(vo.getEmail());
            if (ObjectUtil.isEmpty(sysUser)) {
                throw new MyException("账号不存在");
            }
            //校验账户状态
            sysUserService.validatedUser(sysUser);
            String validCode = (String) RedisUtil.getValue(RedisConstant.LOGIN_EMAIL + sysUser.getId() + ":" + vo.getEmail());
            if (ObjectUtil.isEmpty(validCode)) {
                throw new MyException("邮箱验证码错误或者已失效");
            }
            if (!vo.getValidCode().equals(validCode)) {
                throw new MyException("邮箱验证码错误，请检查");
            }
            RedisUtil.del(RedisConstant.LOGIN_EMAIL + sysUser.getId() + ":" + vo.getPhone());
        } else if (vo.getLoginType().equals(LoginType.PHONE.getCode())) {
            //短信验证码登录
            remark = remark + "/" + LoginType.PHONE.getDesc();
            if (ObjectUtil.hasEmpty(vo.getPhone())) {
                throw new MyException("手机不能为空");
            }
            if (ObjectUtil.hasEmpty(vo.getValidCode())) {
                throw new MyException("手机验证码不能为空");
            }
            Object value = RedisUtil.getValue(RedisConstant.CACHE_UUID + vo.getUuid());
            if (ObjectUtil.isEmpty(value)) {
                throw new MyException("请求不合法");
            }
            sysUser = sysUserService.getUserByPhone(vo.getPhone());
            if (ObjectUtil.isEmpty(sysUser)) {
                throw new MyException("账号不存在");
            }
            //校验账户状态
            sysUserService.validatedUser(sysUser);
            String validCode = (String) RedisUtil.getValue(RedisConstant.LOGIN_PHONE + sysUser.getId() + ":" + vo.getPhone());
            if (ObjectUtil.isEmpty(validCode)) {
                throw new MyException("短信验证码错误或者已失效");
            }
            if (!vo.getValidCode().equals(validCode)) {
                throw new MyException("短信验证码错误，请检查");
            }
            RedisUtil.del(RedisConstant.LOGIN_PHONE + sysUser.getId() + ":" + vo.getPhone());
        } else {
            throw new MyException("错误的loginType");
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
        List<SysRole> roleList = sysRoleService.getSysRoleListByUserId(sysUser.getId());
        List<SysRoleVo> roleVoList = MyUtil.getListOfType(roleList, SysRoleVo.class);
        loginUserVo.setRoles(roleVoList);
        if (loginUserVo.getRoles().size() < 1) {
            //没有角色？肯定不行，最少一个才行，这种情况一般不会存在
            throw new MyException("用户无可用角色");
        }
        Boolean isRoleOk = false;
        for (SysRoleVo role : loginUserVo.getRoles()) {
            if (role.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
                isRoleOk = true;
                break;
            }
        }
        if (!isRoleOk) {
            //用户绑定的角色全部被禁用了
            throw new MyException("用户角色全部不可用");
        }
        List<SysPosition> positionList = sysPositionService.getSysPositionListByUserId(sysUser.getId());
        List<SysPositionVo> positionVoList = MyUtil.getListOfType(positionList, SysPositionVo.class);
        loginUserVo.setPositions(positionVoList);
        if (loginUserVo.getPositions().size() > 0) {
            Boolean isPositionOk = false;
            for (SysPositionVo sysPosition : loginUserVo.getPositions()) {
                if (sysPosition.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
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
        LoginUtil.login(loginUserVo, DeviceType.PC);
        //记录登录日志
        sysLogLoginService.saveLogin(sysUser.getId(), sysUser.getUsername(), SystemConstant.LOGIN_SUCCESS, remark, clientIP, userAgent);
        return StpUtil.getTokenValue();
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
        if (vo.getUsername().contains("@")) {
            throw new MyException("账号不能包含@符号");
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
        if (!sysDeptByCode.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
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
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
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
        String userId = LoginUtil.getLoginUserId();
        List<SysMenu> menuList = sysMenuService.getMenuTreeByUserId(userId);
        return sysMenuService.buildMenus(menuList);
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        LoginUtil.logout();
    }


}
