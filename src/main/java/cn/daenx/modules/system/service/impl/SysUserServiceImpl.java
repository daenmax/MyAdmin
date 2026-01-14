package cn.daenx.modules.system.service.impl;

import cn.daenx.framework.cache.utils.CacheUtil;
import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.domain.vo.CheckSendVo;
import cn.daenx.framework.common.domain.vo.system.config.SysSendLimitConfigVo;
import cn.daenx.framework.common.domain.vo.system.config.SysSmsTemplateConfigVo;
import cn.daenx.framework.common.domain.vo.system.other.SysLoginUserVo;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.notify.email.utils.EmailUtil;
import cn.daenx.framework.notify.sms.domain.SmsSendResult;
import cn.daenx.framework.notify.sms.utils.SmsUtil;
import cn.daenx.framework.oss.domain.UploadResult;
import cn.daenx.framework.satoken.utils.LoginUtil;
import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionPageDto;
import cn.daenx.modules.system.domain.dto.sysRole.SysRolePageDto;
import cn.daenx.modules.system.domain.dto.sysUser.*;
import cn.daenx.modules.system.domain.po.*;
import cn.daenx.modules.system.domain.vo.sysUser.SysUserPageVo;
import cn.daenx.modules.system.mapper.SysUserDetailMapper;
import cn.daenx.modules.system.mapper.SysUserMapper;
import cn.daenx.modules.system.service.*;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserDetailMapper sysUserDetailMapper;
    @Resource
    private SysUserDetailService sysUserDetailService;
    @Resource
    private SysRoleUserService sysRoleUserService;
    @Resource
    private SysUserDeptService sysUserDeptService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysPositionService sysPositionService;
    @Resource
    private SysPositionUserService sysPositionUserService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysDictDetailService sysDictDetailService;

    @Resource
    private SysFileService sysFileService;
    @Resource
    private SysConfigService sysConfigService;

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 通过手机号检查用户是否存在
     *
     * @param phone
     * @param nowId 排除ID
     * @return
     */
    @Override
    public Boolean checkUserByPhone(String phone, String nowId) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getPhone, phone);
        wrapper.ne(ObjectUtil.isNotEmpty(nowId), SysUser::getId, nowId);
        return sysUserMapper.exists(wrapper);
    }

    /**
     * 通过邮箱检查用户是否存在
     *
     * @param email
     * @param nowId 排除ID
     * @return
     */
    @Override
    public Boolean checkUserByEmail(String email, String nowId) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getEmail, email);
        wrapper.ne(ObjectUtil.isNotEmpty(nowId), SysUser::getId, nowId);
        return sysUserMapper.exists(wrapper);
    }

    /**
     * 通过openId检查用户是否存在
     *
     * @param openId
     * @param nowId  排除ID
     * @return
     */
    @Override
    public Boolean checkUserByOpenId(String openId, String nowId) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getOpenId, openId);
        wrapper.ne(ObjectUtil.isNotEmpty(nowId), SysUser::getId, nowId);
        return sysUserMapper.exists(wrapper);
    }

    /**
     * 通过apiKey检查用户是否存在
     *
     * @param apiKey
     * @return
     */
    @Override
    public Boolean checkUserByApiKey(String apiKey) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getApiKey, apiKey);
        wrapper.eq(SysUser::getStatus, CommonConstant.STATUS_NORMAL);
        return sysUserMapper.exists(wrapper);
    }

    /**
     * 通过账号获取用户
     *
     * @param username
     * @return
     */
    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return sysUserMapper.selectOne(wrapper);
    }

    /**
     * 通过邮箱获取用户
     *
     * @param email
     * @return
     */
    @Override
    public SysUser getUserByEmail(String email) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getEmail, email);
        return sysUserMapper.selectOne(wrapper);
    }

    /**
     * 通过手机获取用户
     *
     * @param phone
     * @return
     */
    @Override
    public SysUser getUserByPhone(String phone) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getPhone, phone);
        return sysUserMapper.selectOne(wrapper);
    }

    /**
     * 通过ID获取用户
     *
     * @param userId
     * @return
     */
    @Override
    public SysUser getUserByUserId(String userId) {
        return sysUserMapper.selectById(userId);
    }

    /**
     * 通过ID获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public SysUserPageVo getUserInfoByUserId(String userId) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("su.id", userId);
        wrapper.eq("su.is_delete", SystemConstant.IS_DELETE_NO);
        SysUserPageVo userInfoByUserId = sysUserMapper.getUserInfoByUserId(wrapper);
        userInfoByUserId.setAdmin(CommonConstant.SUPER_ADMIN.equals(userInfoByUserId.getId()));
        return userInfoByUserId;
    }

    /**
     * 校验用户状态是否正常
     *
     * @param sysUser
     * @return
     */
    @Override
    public Boolean validatedUser(SysUser sysUser) {
        if (SystemConstant.USER_STATUS_DISABLE.equals(sysUser.getStatus())) {
            throw new MyException("账号已被停用");
        }
        if (SystemConstant.USER_STATUS_OFF.equals(sysUser.getStatus())) {
            throw new MyException("账号已注销");
        }
        //判断是否锁定中
        if (ObjectUtil.isNotEmpty(sysUser.getBanToTime())) {
            if (LocalDateTime.now().isBefore(sysUser.getBanToTime())) {
                String banToTime = LocalDateTimeUtil.format(sysUser.getBanToTime(), CommonConstant.DATE_TIME_FORMAT);
                throw new MyException("账号被锁定，自动解除时间：" + banToTime);
            }
        }
        if (SystemConstant.USER_STATUS_NORMAL.equals(sysUser.getStatus())) {
            //正常，需要判断是否到期
            if (ObjectUtil.isEmpty(sysUser.getExpireToTime())) {
                return true;
            } else {
                if (LocalDateTime.now().isAfter(sysUser.getExpireToTime())) {
                    String expireToTime = LocalDateTimeUtil.format(sysUser.getExpireToTime(), CommonConstant.DATE_TIME_FORMAT);
                    throw new MyException("账号已到期，到期时间：" + expireToTime);
                }
            }
        }
        return true;
    }

    /**
     * 注册用户
     *
     * @param sysUser
     * @param deptCodes     部门编码
     * @param roleCodes     角色编码
     * @param positionCodes 岗位编码
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean registerUser(SysUser sysUser, String[] deptCodes, String[] roleCodes, String[] positionCodes) {
        sysUserMapper.insert(sysUser);
        //创建用户、详细信息关联
        SysUserDetail sysUserDetail = new SysUserDetail();
        sysUserDetail.setUserId(sysUser.getId());
        Boolean detail = sysUserDetailService.createDetail(sysUserDetail);
        if (!detail) {
            throw new MyException("创建用户信息失败");
        }

        //创建用户、角色关联
        LambdaQueryWrapper<SysRole> wrapperRole = new LambdaQueryWrapper<>();
        wrapperRole.in(SysRole::getCode, Arrays.asList(roleCodes));
        wrapperRole.eq(SysRole::getStatus, CommonConstant.STATUS_NORMAL);
        List<SysRole> sysRoleList = sysRoleService.list(wrapperRole);
        List<String> roleIds = MyUtil.joinToList(sysRoleList, SysRole::getId);
        sysRoleUserService.handleUserRole(sysUser.getId(), roleIds);

        //创建用户、部门关联
        LambdaQueryWrapper<SysDept> wrapperDept = new LambdaQueryWrapper<>();
        wrapperDept.in(SysDept::getCode, Arrays.asList(roleCodes));
        wrapperDept.eq(SysDept::getStatus, CommonConstant.STATUS_NORMAL);
        List<SysDept> sysDeptList = sysDeptService.list(wrapperDept);
        List<String> deptIds = MyUtil.joinToList(sysDeptList, SysDept::getId);
        sysUserDeptService.handleUserDept(sysUser.getId(), deptIds);

        //创建用户、岗位关联
        if (ObjectUtil.isNotEmpty(positionCodes) && positionCodes.length > 0) {
            LambdaQueryWrapper<SysPosition> wrapperPosition = new LambdaQueryWrapper<>();
            wrapperPosition.in(SysPosition::getCode, Arrays.asList(roleCodes));
            wrapperPosition.eq(SysPosition::getStatus, CommonConstant.STATUS_NORMAL);
            List<SysPosition> sysPositionList = sysPositionService.list(wrapperPosition);
            List<String> positionIds = MyUtil.joinToList(sysPositionList, SysPosition::getId);
            sysPositionUserService.handleUserPosition(sysUser.getId(), positionIds);
        }
        return true;
    }

    /**
     * 个人信息
     *
     * @return
     */
    @Override
    public Map<String, Object> profile() {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        SysUserPageVo sysUserVo = getUserInfoByUserId(loginUser.getId());
        if (sysUserVo == null) {
            throw new MyException("用户不存在");
        }
        sysUserVo.setUserTypeName(sysDictDetailService.getDictDetailValueByCodeFromRedis("sys_user_type", sysUserVo.getUserType()).getLabel());
        Map<String, Object> map = new HashMap<>();
        map.put("user", sysUserVo);
        List<SysRole> roleList = sysRoleService.getSysRoleListByUserId(sysUserVo.getId());
        List<SysPosition> positionList = sysPositionService.getSysPositionListByUserId(sysUserVo.getId());
        map.put("roleGroup", MyUtil.joinToList(roleList, SysRole::getName));
        map.put("postGroup", MyUtil.joinToList(positionList, SysPosition::getName));
        return map;
    }

    /**
     * 修改个人资料
     *
     * @param dto
     */
    @Override
    public void updInfo(SysUserUpdInfoDto dto) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        LambdaUpdateWrapper<SysUserDetail> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUserDetail::getUserId, loginUser.getId());
        wrapper.set(SysUserDetail::getNickName, dto.getNickName());
        wrapper.set(SysUserDetail::getRealName, dto.getRealName());
        wrapper.set(SysUserDetail::getAge, dto.getAge());
        wrapper.set(SysUserDetail::getSex, dto.getSex());
        wrapper.set(SysUserDetail::getProfile, dto.getProfile());
        wrapper.set(SysUserDetail::getUserSign, dto.getUserSign());
        int update = sysUserDetailMapper.update(new SysUserDetail(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
    }

    /**
     * 修改个人密码
     *
     * @param dto
     */
    @Override
    public void updatePwd(SysUserUpdPwdDto dto) {
        if (dto.getNewPassword().equals(dto.getOldPassword())) {
            throw new MyException("新密码不能与旧密码一样");
        }
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        SysUser sysUser = getUserByUserId(loginUser.getId());
        String sha256 = SaSecureUtil.sha256(dto.getOldPassword());
        if (!sha256.equals(sysUser.getPassword())) {
            throw new MyException("旧密码输入错误");
        }
        String newPwd = SaSecureUtil.sha256(dto.getNewPassword());
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getId, sysUser.getId());
        wrapper.set(SysUser::getPassword, newPwd);
        int update = sysUserMapper.update(new SysUser(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        LoginUtil.logout();
    }


    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<SysUserPageVo> getPage(SysUserPageDto dto) {
        QueryWrapper<SysUser> wrapper = getWrapper(dto);
        dto.setOrderByColumn("su.create_time");
        dto.setIsAsc("desc");
        IPage<SysUserPageVo> sysUserPage = sysUserMapper.getPageWrapper(dto.getPage(false), wrapper);
        for (SysUserPageVo record : sysUserPage.getRecords()) {
            record.setAdmin(CommonConstant.SUPER_ADMIN.equals(record.getId()));
        }
        return sysUserPage;
    }

    /**
     * 导出
     *
     * @param dto
     * @return
     */
    @Override
    public List<SysUserPageVo> exportData(SysUserPageDto dto) {
        QueryWrapper<SysUser> wrapper = getWrapper(dto);
        List<SysUserPageVo> list = sysUserMapper.getAll(wrapper);
        for (SysUserPageVo record : list) {
            record.setAdmin(CommonConstant.SUPER_ADMIN.equals(record.getId()));
        }
        return list;
    }

    private QueryWrapper<SysUser> getWrapper(SysUserPageDto dto) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(dto.getDeptId())) {
            List<SysDept> listByParentId = sysDeptService.getListByParentId(dto.getDeptId(), true);
            List<String> ids = MyUtil.joinToList(listByParentId, SysDept::getId);
            wrapper.inSql("su.id", "select sys_user_dept.user_id from sys_user_dept where sys_user_dept.dept_id in('" + MyUtil.join(ids, String::trim, "','") + "')");
        }
        wrapper.like(ObjectUtil.isNotEmpty(dto.getUsername()), "su.username", dto.getUsername());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), "su.status", dto.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getPhone()), "su.phone", dto.getPhone());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getEmail()), "su.email", dto.getEmail());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRemark()), "su.remark", dto.getRemark());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getUserType()), "su.user_type", dto.getUserType());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getNickName()), "sud.nick_name", dto.getNickName());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRealName()), "sud.real_name", dto.getRealName());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getAge()), "sud.age", dto.getAge());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getSex()), "sud.sex", dto.getSex());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getProfile()), "sud.profile", dto.getProfile());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getUserSign()), "sud.user_sign", dto.getUserSign());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), "su.create_time", startTime, endTime);
        wrapper.eq("su.is_delete", SystemConstant.IS_DELETE_NO);
        return wrapper;
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getInfo(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", sysRoleService.getAll(new SysRolePageDto()));
        map.put("positions", sysPositionService.getAll(new SysPositionPageDto()));
//        map.put("depts", sysDeptService.getAllNoLeaderUser(new SysDeptPageVo()));
        if (ObjectUtil.isNotEmpty(id)) {
            SysUserPageVo sysUserByPermissions = getSysUserByPermissions(id);
            map.put("user", sysUserByPermissions);
            map.put("roleIds", MyUtil.joinToList(sysUserByPermissions.getRoles(), SysRole::getId));
            map.put("positionIds", MyUtil.joinToList(sysUserByPermissions.getPositions(), SysPosition::getId));
            map.put("deptIds", MyUtil.joinToList(sysUserByPermissions.getDepts(), SysDept::getId));
        }
        return map;
    }

    /**
     * 获取权限范围内的该用户，无权限则抛出异常
     *
     * @param id
     * @return
     */
    private SysUserPageVo getSysUserByPermissions(String id) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("su.id", id);
        wrapper.eq("su.is_delete", SystemConstant.IS_DELETE_NO);
        SysUserPageVo info = sysUserMapper.getInfo(wrapper);
        if (ObjectUtil.isEmpty(info)) {
            throw new MyException("你无权限操作该数据");
        }
        info.setAdmin(CommonConstant.SUPER_ADMIN.equals(info.getId()));
        return info;
    }

    /**
     * 修改
     *
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editInfo(SysUserUpdDto dto) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        if (loginUser.getId().equals(dto.getId())) {
            throw new MyException("禁止操作自己");
        }
        if (!loginUser.getIsAdmin() && sysUserMapper.isAdmin(SystemConstant.ROLE_ADMIN, dto.getId())) {
            throw new MyException("禁止操作超级管理员");
        }
        if (sysRoleService.isHasAdmin(SystemConstant.ROLE_ADMIN, dto.getRoleIds())) {
            throw new MyException("禁止修改为超级管理员");
        }
        SysUserPageVo sysUserByPermissions = getSysUserByPermissions(dto.getId());
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, dto.getId());
        updateWrapper.set(SysUser::getStatus, dto.getStatus());
        updateWrapper.set(SysUser::getPhone, dto.getPhone());
        updateWrapper.set(SysUser::getEmail, dto.getEmail());
        updateWrapper.set(SysUser::getOpenId, dto.getOpenId());
        updateWrapper.set(SysUser::getApiKey, dto.getApiKey());
        updateWrapper.set(SysUser::getBanToTime, dto.getBanToTime());
        updateWrapper.set(SysUser::getExpireToTime, dto.getExpireToTime());
        updateWrapper.set(SysUser::getUserType, dto.getUserType());
        updateWrapper.set(SysUser::getRemark, dto.getRemark());
        int rows = sysUserMapper.update(new SysUser(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        //修改detail
        LambdaUpdateWrapper<SysUserDetail> updateWrapperDetail = new LambdaUpdateWrapper<>();
        updateWrapperDetail.eq(SysUserDetail::getUserId, dto.getId());
        updateWrapperDetail.set(SysUserDetail::getNickName, dto.getNickName());
        updateWrapperDetail.set(SysUserDetail::getRealName, dto.getRealName());
        updateWrapperDetail.set(SysUserDetail::getAge, dto.getAge());
        updateWrapperDetail.set(SysUserDetail::getSex, dto.getSex());
        updateWrapperDetail.set(SysUserDetail::getProfile, dto.getProfile());
        updateWrapperDetail.set(SysUserDetail::getUserSign, dto.getUserSign());
        updateWrapperDetail.set(SysUserDetail::getMoney, dto.getMoney());
        int rowsDetail = sysUserDetailMapper.update(new SysUserDetail(), updateWrapperDetail);
        if (rowsDetail < 1) {
            throw new MyException("修改失败");
        }
        //修改关联数据
        sysRoleUserService.handleUserRole(dto.getId(), dto.getRoleIds());
        sysPositionUserService.handleUserPosition(dto.getId(), dto.getPositionIds());
        sysUserDeptService.handleUserDept(dto.getId(), dto.getDeptIds());
        //注销该账户的登录
        LoginUtil.logoutByUsername(sysUserByPermissions.getUsername());
    }

    /**
     * 新增
     *
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addInfo(SysUserAddDto dto) {
        if (dto.getUsername().contains("@")) {
            throw new MyException("账号不能包含@符号");
        }
        if (StringUtils.isNotBlank(dto.getPhone())) {
            if (checkUserByPhone(dto.getPhone(), null)) {
                throw new MyException("手机号已存在");
            }
        }
        if (StringUtils.isNotBlank(dto.getEmail())) {
            if (checkUserByEmail(dto.getEmail(), null)) {
                throw new MyException("邮箱已存在");
            }
        }
        if (StringUtils.isNotBlank(dto.getOpenId())) {
            if (checkUserByOpenId(dto.getOpenId(), null)) {
                throw new MyException("openId已存在");
            }
        }
        if (StringUtils.isNotBlank(dto.getApiKey())) {
            if (checkUserByApiKey(dto.getApiKey())) {
                throw new MyException("apiKey已存在");
            }
        }
        if (sysRoleService.isHasAdmin(SystemConstant.ROLE_ADMIN, dto.getRoleIds())) {
            throw new MyException("禁止新增为超级管理员");
        }
        SysUser userByUsername = getUserByUsername(dto.getUsername());
        if (ObjectUtil.isNotEmpty(userByUsername)) {
            throw new MyException("用户账号已存在");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUsername(dto.getUsername());
        String newPwd = SaSecureUtil.sha256(dto.getPassword());
        sysUser.setPassword(newPwd);
        sysUser.setStatus(dto.getStatus());
        sysUser.setPhone(dto.getPhone());
        sysUser.setEmail(dto.getEmail());
        sysUser.setOpenId(dto.getOpenId());
        sysUser.setApiKey(dto.getApiKey());
        sysUser.setBanToTime(dto.getBanToTime());
        sysUser.setExpireToTime(dto.getExpireToTime());
        sysUser.setRemark(dto.getRemark());
        sysUser.setUserType(dto.getUserType());
        int rows = sysUserMapper.insert(sysUser);
        if (rows < 1) {
            throw new MyException("新增失败");
        }
        SysUserDetail sysUserDetail = new SysUserDetail();
        sysUserDetail.setUserId(sysUser.getId());
        sysUserDetail.setNickName(dto.getNickName());
        sysUserDetail.setRealName(dto.getRealName());
        sysUserDetail.setAge(dto.getAge());
        sysUserDetail.setSex(dto.getSex());
        sysUserDetail.setProfile(dto.getProfile());
        sysUserDetail.setUserSign(dto.getUserSign());
        sysUserDetail.setAvatar(dto.getAvatar());
        sysUserDetail.setMoney(dto.getMoney());
        int rowsDetail = sysUserDetailMapper.insert(sysUserDetail);
        if (rowsDetail < 1) {
            throw new MyException("新增失败");
        }
        //新增关联数据
        sysRoleUserService.handleUserRole(sysUser.getId(), dto.getRoleIds());
        sysPositionUserService.handleUserPosition(sysUser.getId(), dto.getPositionIds());
        sysUserDeptService.handleUserDept(sysUser.getId(), dto.getDeptIds());
    }

    /**
     * 修改状态
     *
     * @param dto
     */
    @Override
    public void changeStatus(ComStatusUpdDto dto) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        if (loginUser.getId().equals(dto.getId())) {
            throw new MyException("禁止操作自己");
        }
        if (!loginUser.getIsAdmin() && sysUserMapper.isAdmin(SystemConstant.ROLE_ADMIN, dto.getId())) {
            throw new MyException("禁止操作超级管理员");
        }
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, dto.getId());
        updateWrapper.set(SysUser::getStatus, dto.getStatus());
        int rows = sysUserMapper.update(new SysUser(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
    }

    /**
     * 重置用户密码
     *
     * @param dto
     */
    @Override
    public void resetPwd(SysUserResetPwdDto dto) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        if (loginUser.getId().equals(dto.getId())) {
            throw new MyException("禁止操作自己");
        }
        if (!loginUser.getIsAdmin() && sysUserMapper.isAdmin(SystemConstant.ROLE_ADMIN, dto.getId())) {
            throw new MyException("禁止操作超级管理员");
        }
        SysUserPageVo sysUserByPermissions = getSysUserByPermissions(dto.getId());
        String newPwd = SaSecureUtil.sha256(dto.getPassword());
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, dto.getId());
        updateWrapper.set(SysUser::getPassword, newPwd);
        int rows = sysUserMapper.update(new SysUser(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        //注销该账户的登录
        LoginUtil.logoutByUsername(sysUserByPermissions.getUsername());
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        if (ids.contains(loginUser.getId())) {
            throw new MyException("不能删除自己");
        }
        if (!loginUser.getIsAdmin() && sysUserMapper.isHasAdmin(SystemConstant.ROLE_ADMIN, ids)) {
            throw new MyException("禁止操作超级管理员");
        }
        //获取有权限操作的用户
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("su.id", ids);
        List<SysUser> sysUsers = sysUserMapper.getUserList(queryWrapper);
        if (sysUsers.size() > 0) {
            for (SysUser sysUser : sysUsers) {
                //注销该账户的登录
                LoginUtil.logoutByUsername(sysUser.getUsername());
            }
            List<String> realList = MyUtil.joinToList(sysUsers, SysUser::getId);
            //删除主表
            sysUserMapper.deleteByIds(realList);
            //删除详细表
            LambdaUpdateWrapper<SysUserDetail> wrapper = new LambdaUpdateWrapper<>();
            wrapper.in(SysUserDetail::getUserId, realList);
            sysUserDetailMapper.delete(wrapper);
            //删除关联数据
            sysRoleUserService.delUserRole(realList);
            sysPositionUserService.delUserPosition(realList);
        }
    }

    /**
     * 根据用户编号获取授权角色
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> authRole(String id) {
        Map<String, Object> map = new HashMap<>();
        List<SysRole> sysRoleList = sysRoleService.getAll(new SysRolePageDto());
        SysUserPageVo sysUserByPermissions = getSysUserByPermissions(id);
        List<SysRole> userRoles = sysUserByPermissions.getRoles();
        for (SysRole sysRole : sysRoleList) {
            for (SysRole userRole : userRoles) {
                if (sysRole.getId().equals(userRole.getId())) {
                    sysRole.setFlag(true);
                }
            }
        }
        map.put("roles", sysRoleList);
        map.put("user", sysUserByPermissions);
        return map;
    }

    /**
     * 保存用户授权角色
     *
     * @param dto
     */
    @Override
    public void saveAuthRole(SysUserUpdAuthRoleDto dto) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        if (loginUser.getId().equals(dto.getUserId())) {
            throw new MyException("禁止操作自己");
        }
        if (!loginUser.getIsAdmin() && sysUserMapper.isAdmin(SystemConstant.ROLE_ADMIN, dto.getUserId())) {
            throw new MyException("禁止操作超级管理员");
        }
        SysUserPageVo sysUserByPermissions = getSysUserByPermissions(dto.getUserId());
        //修改关联数据
        sysRoleUserService.handleUserRole(dto.getUserId(), dto.getRoleIds());
        //注销该账户的登录
        LoginUtil.logoutByUsername(sysUserByPermissions.getUsername());
    }

    /**
     * 查询已分配该角色的用户列表
     *
     * @param dto
     * @param roleId
     * @return
     */
    @Override
    public IPage<SysUserPageVo> getUserListByRoleId(SysUserPageDto dto, String roleId) {
        QueryWrapper<SysUser> wrapper = getWrapper(dto);
        wrapper.exists("SELECT * FROM sys_role_user sur WHERE sur.role_id = '" + roleId + "' AND sur.user_id = su.id");
        IPage<SysUserPageVo> sysUserPage = sysUserMapper.getPageWrapper(dto.getPage(true), wrapper);
        for (SysUserPageVo record : sysUserPage.getRecords()) {
            record.setAdmin(CommonConstant.SUPER_ADMIN.equals(record.getId()));
        }
        return sysUserPage;
    }

    /**
     * 查询未分配该角色的用户列表
     *
     * @param dto
     * @param roleId
     * @return
     */
    @Override
    public IPage<SysUserPageVo> getUserListByUnRoleId(SysUserPageDto dto, String roleId) {
        QueryWrapper<SysUser> wrapper = getWrapper(dto);
        wrapper.notExists("SELECT * FROM sys_role_user sur WHERE sur.role_id = '" + roleId + "' AND sur.user_id = su.id");
        IPage<SysUserPageVo> sysUserPage = sysUserMapper.getPageWrapper(dto.getPage(true), wrapper);
        for (SysUserPageVo record : sysUserPage.getRecords()) {
            record.setAdmin(CommonConstant.SUPER_ADMIN.equals(record.getId()));
        }
        return sysUserPage;
    }

    /**
     * 查询已分配该岗位的用户列表
     *
     * @param dto
     * @param positionId
     * @return
     */
    @Override
    public IPage<SysUserPageVo> getUserListByPositionId(SysUserPageDto dto, String positionId) {
        QueryWrapper<SysUser> wrapper = getWrapper(dto);
        wrapper.exists("SELECT * FROM sys_position_user spr WHERE spr.position_id = '" + positionId + "' AND spr.user_id = su.id");
        IPage<SysUserPageVo> sysUserPage = sysUserMapper.getPageWrapper(dto.getPage(true), wrapper);
        for (SysUserPageVo record : sysUserPage.getRecords()) {
            record.setAdmin(CommonConstant.SUPER_ADMIN.equals(record.getId()));
        }
        return sysUserPage;
    }

    /**
     * 查询未分配该岗位的用户列表
     *
     * @param dto
     * @param positionId
     * @return
     */
    @Override
    public IPage<SysUserPageVo> getUserListByUnPositionId(SysUserPageDto dto, String positionId) {
        QueryWrapper<SysUser> wrapper = getWrapper(dto);
        wrapper.notExists("SELECT * FROM sys_position_user spr WHERE spr.position_id = '" + positionId + "' AND spr.user_id = su.id");
        IPage<SysUserPageVo> sysUserPage = sysUserMapper.getPageWrapper(dto.getPage(true), wrapper);
        for (SysUserPageVo record : sysUserPage.getRecords()) {
            record.setAdmin(CommonConstant.SUPER_ADMIN.equals(record.getId()));
        }
        return sysUserPage;
    }

    /**
     * 获取用户列表
     *
     * @param id       如果传ID，则会忽略其他全部参数
     * @param username
     * @param nickName
     * @param phone
     * @param email
     * @return
     */
    @Override
    public List<SysUserPageVo> getUserList(String id, String username, String nickName, String realName, String phone, String email) {
        List<SysUserPageVo> list = new ArrayList<>();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("su.is_delete", SystemConstant.IS_DELETE_NO);
        if (ObjectUtil.isNotEmpty(id)) {
            wrapper.eq("su.id", id);
        } else {
            if (ObjectUtil.isEmpty(username) && ObjectUtil.isEmpty(realName) && ObjectUtil.isEmpty(phone) && ObjectUtil.isEmpty(email)) {
                return list;
            }
            wrapper.and(wq -> {
                // 拼接sql
                wq.like(ObjectUtil.isNotEmpty(username), "su.username", username).or();
                wq.like(ObjectUtil.isNotEmpty(nickName), "sud.nick_name", nickName).or();
                wq.like(ObjectUtil.isNotEmpty(realName), "sud.real_name", realName).or();
                wq.like(ObjectUtil.isNotEmpty(phone), "su.phone", phone).or();
                wq.like(ObjectUtil.isNotEmpty(email), "su.email", email).or();
            });
        }
        list = sysUserMapper.getAll(wrapper);
        for (SysUserPageVo record : list) {
            record.setAdmin(CommonConstant.SUPER_ADMIN.equals(record.getId()));
        }
        return list;
    }

    /**
     * 修改头像
     * 返回头像链接
     *
     * @param file
     * @return
     */
    @Override
    public String avatar(MultipartFile file) {
        UploadResult uploadResult = sysFileService.uploadImage(file, SystemConstant.FILE_FROM_AVATAR);
        String loginUserId = LoginUtil.getLoginUserId();
        LambdaUpdateWrapper<SysUserDetail> updateWrapperDetail = new LambdaUpdateWrapper<>();
        updateWrapperDetail.eq(SysUserDetail::getUserId, loginUserId);
        updateWrapperDetail.set(SysUserDetail::getAvatar, uploadResult.getSysFileId());
        int rowsDetail = sysUserDetailMapper.update(new SysUserDetail(), updateWrapperDetail);
        if (rowsDetail < 1) {
            throw new MyException("修改失败");
        }
        return uploadResult.getFileUrl();
    }

    /**
     * 获取邮箱验证码
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> getEmailValidCode(SysUserUpdBindDto dto) {
        if (ObjectUtil.isEmpty(dto.getEmail())) {
            throw new MyException("请填写邮箱");
        }
        SysSendLimitConfigVo sysSendLimitConfigVo = sysConfigService.getSysSendLimitConfigVo();
        Long keepLive = 0L;
        if (sysSendLimitConfigVo != null) {
            keepLive = Long.valueOf(sysSendLimitConfigVo.getEmail().getKeepLive());
        }
        //例如：5分钟
        String keepLiveStr = MyUtil.timeDistance(keepLive * 1000);
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        Boolean exist = checkUserByEmail(dto.getEmail(), loginUser.getId());
        if (exist) {
            throw new MyException("该邮箱已经被其他账户绑定");
        }
        if (dto.getEmail().equals(loginUser.getEmail())) {
            throw new MyException("当前账户已经绑定了此邮箱");
        }
        String value = (String) CacheUtil.getValue(RedisConstant.VALIDA_EMAIL + loginUser.getId() + ":" + dto.getEmail());
        if (ObjectUtil.isNotEmpty(value)) {
            throw new MyException("验证码尚未失效，如未收到验证码请" + keepLiveStr + "后再试");
        }
        CheckSendVo checkSendVo = EmailUtil.checkSendByUserId(loginUser.getId(), sysSendLimitConfigVo);
        if (!checkSendVo.getNowOk()) {
            throw new MyException(checkSendVo.getMsg());
        }
        //生成验证码
        String code = RandomUtil.randomNumbers(6);
        String subject = "【" + applicationName + "】" + "邮件验证";
        String content = MyUtil.buildCodeValida(applicationName, code);
        Boolean aBoolean = EmailUtil.sendEmail(dto.getEmail(), subject, content, true, null);
        if (!aBoolean) {
            throw new MyException("发送邮件失败，请联系管理员");
        }
        CacheUtil.setValue(RedisConstant.VALIDA_EMAIL + loginUser.getId() + ":" + dto.getEmail(), code, keepLive, TimeUnit.SECONDS);
        Integer waitTime = EmailUtil.saveSendByUserId(loginUser.getId(), sysSendLimitConfigVo);
        Map<String, Object> map = new HashMap<>();
        map.put("waitTime", waitTime);
        map.put("msg", "验证码已发送，" + keepLiveStr + "有效");
        return map;
    }

    /**
     * 获取手机验证码
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> getPhoneValidCode(SysUserUpdBindDto dto) {
        if (ObjectUtil.isEmpty(dto.getPhone())) {
            throw new MyException("请填写手机号");
        }
        SysSendLimitConfigVo sysSendLimitConfigVo = sysConfigService.getSysSendLimitConfigVo();
        Long keepLive = 0L;
        if (sysSendLimitConfigVo != null) {
            keepLive = Long.valueOf(sysSendLimitConfigVo.getSms().getKeepLive());
        }
        //例如：5分钟
        String keepLiveStr = MyUtil.timeDistance(keepLive * 1000);
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        Boolean exist = checkUserByPhone(dto.getPhone(), loginUser.getId());
        if (exist) {
            throw new MyException("该手机号已经被其他账户绑定");
        }
        if (dto.getPhone().equals(loginUser.getPhone())) {
            throw new MyException("当前账户已经绑定了此手机号");
        }
        String value = (String) CacheUtil.getValue(RedisConstant.VALIDA_PHONE + loginUser.getId() + ":" + dto.getPhone());
        if (ObjectUtil.isNotEmpty(value)) {
            throw new MyException("验证码尚未失效，如未收到验证码请" + keepLiveStr + "后再试");
        }
        CheckSendVo checkSendVo = SmsUtil.checkSendByUserId(loginUser.getId(), sysSendLimitConfigVo);
        if (!checkSendVo.getNowOk()) {
            throw new MyException(checkSendVo.getMsg());
        }
        SysSmsTemplateConfigVo sysSmsTemplateConfigVo = sysConfigService.getSysSmsTemplateConfigVo();
        if (ObjectUtil.isEmpty(sysSmsTemplateConfigVo)) {
            throw new MyException("没有配置短信模板参数，请联系管理员");
        }
        if (ObjectUtil.isEmpty(sysSmsTemplateConfigVo.getBindPhone())) {
            throw new MyException("没有配置bindPhone短信模板参数，请联系管理员");
        }
        //生成验证码
        String code = RandomUtil.randomNumbers(6);
        Map<String, String> smsMap = new HashMap<>();
        smsMap.put(sysSmsTemplateConfigVo.getBindPhone().getVariable(), code);
        SmsSendResult smsSendResult = SmsUtil.sendSms(dto.getPhone(), sysSmsTemplateConfigVo.getBindPhone().getTemplateId(), smsMap);
        if (!smsSendResult.isSuccess()) {
            throw new MyException("发送短信失败，请联系管理员");
        }
        CacheUtil.setValue(RedisConstant.VALIDA_PHONE + loginUser.getId() + ":" + dto.getPhone(), code, keepLive, TimeUnit.SECONDS);
        Integer waitTime = SmsUtil.saveSendByUserId(loginUser.getId(), sysSendLimitConfigVo);
        Map<String, Object> map = new HashMap<>();
        map.put("waitTime", waitTime);
        map.put("msg", "验证码已发送，" + keepLiveStr + "有效");
        return map;
    }

    /**
     * 修改邮箱绑定
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> updateBindEmail(SysUserUpdBindDto dto) {
        if (ObjectUtil.isEmpty(dto.getEmail())) {
            throw new MyException("请填写邮箱");
        }
        if (ObjectUtil.isEmpty(dto.getValidCode())) {
            throw new MyException("请填写收到的邮箱验证码");
        }
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        String validCode = (String) CacheUtil.getValue(RedisConstant.VALIDA_EMAIL + loginUser.getId() + ":" + dto.getEmail());
        if (ObjectUtil.isEmpty(validCode)) {
            throw new MyException("邮箱验证码错误或者已失效");
        }
        if (!dto.getValidCode().equals(validCode)) {
            throw new MyException("邮箱验证码错误，请检查");
        }
        CacheUtil.del(RedisConstant.VALIDA_EMAIL + loginUser.getId() + ":" + dto.getEmail());
        Boolean exist = checkUserByEmail(dto.getEmail(), loginUser.getId());
        if (exist) {
            throw new MyException("该邮箱已经被其他账户绑定");
        }
        if (dto.getEmail().equals(loginUser.getEmail())) {
            throw new MyException("当前账户已经绑定了此邮箱");
        }
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getId, loginUser.getId());
        wrapper.set(SysUser::getEmail, dto.getEmail());
        int update = sysUserMapper.update(new SysUser(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        LoginUtil.logout();
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "绑定成功，请重新登录");
        return map;
    }

    /**
     * 修改手机绑定
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> updateBindPhone(SysUserUpdBindDto dto) {
        if (ObjectUtil.isEmpty(dto.getPhone())) {
            throw new MyException("请填写手机号");
        }
        if (ObjectUtil.isEmpty(dto.getValidCode())) {
            throw new MyException("请填写收到的短信验证码");
        }
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        String validCode = (String) CacheUtil.getValue(RedisConstant.VALIDA_PHONE + loginUser.getId() + ":" + dto.getPhone());
        if (ObjectUtil.isEmpty(validCode)) {
            throw new MyException("短信验证码错误或者已失效");
        }
        if (!dto.getValidCode().equals(validCode)) {
            throw new MyException("短信验证码错误，请检查");
        }
        CacheUtil.del(RedisConstant.VALIDA_PHONE + loginUser.getId() + ":" + dto.getPhone());
        Boolean exist = checkUserByPhone(dto.getPhone(), loginUser.getId());
        if (exist) {
            throw new MyException("该手机号已经被其他账户绑定");
        }
        if (dto.getPhone().equals(loginUser.getPhone())) {
            throw new MyException("当前账户已经绑定了此手机号");
        }
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getId, loginUser.getId());
        wrapper.set(SysUser::getPhone, dto.getPhone());
        int update = sysUserMapper.update(new SysUser(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        LoginUtil.logout();
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "绑定成功，请重新登录");
        return map;
    }
}
