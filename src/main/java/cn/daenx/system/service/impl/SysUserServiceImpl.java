package cn.daenx.system.service.impl;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.system.config.SysSendLimitConfigVo;
import cn.daenx.framework.common.vo.system.config.SysSmsTemplateConfigVo;
import cn.daenx.framework.common.vo.system.other.SysLoginUserVo;
import cn.daenx.framework.notify.sms.vo.SmsSendResult;
import cn.daenx.framework.oss.vo.UploadResult;
import cn.daenx.framework.notify.email.utils.EmailUtil;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.common.utils.RedisUtil;
import cn.daenx.framework.notify.sms.utils.SmsUtil;
import cn.daenx.framework.common.vo.CheckSendVo;
import cn.daenx.framework.common.vo.ComStatusUpdVo;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.satoken.utils.LoginUtil;
import cn.daenx.system.domain.dto.SysUserPageDto;
import cn.daenx.system.domain.po.*;
import cn.daenx.system.domain.vo.*;
import cn.daenx.system.mapper.SysUserDetailMapper;
import cn.daenx.system.mapper.SysUserMapper;

import cn.daenx.system.service.*;

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
    private CaptchaService captchaService;
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
    public SysUserPageDto getUserInfoByUserId(String userId) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("su.id", userId);
        wrapper.eq("su.is_delete", SystemConstant.IS_DELETE_NO);
        SysUserPageDto userInfoByUserId = sysUserMapper.getUserInfoByUserId(wrapper);
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
     * @param roleCodes     角色编码
     * @param positionCodes 岗位编码
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean registerUser(SysUser sysUser, String[] roleCodes, String[] positionCodes) {
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
        SysUserPageDto sysUserVo = getUserInfoByUserId(loginUser.getId());
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
     * @param vo
     */
    @Override
    public void updInfo(SysUserUpdInfoVo vo) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        LambdaUpdateWrapper<SysUserDetail> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUserDetail::getUserId, loginUser.getId());
        wrapper.set(SysUserDetail::getNickName, vo.getNickName());
        wrapper.set(SysUserDetail::getRealName, vo.getRealName());
        wrapper.set(SysUserDetail::getAge, vo.getAge());
        wrapper.set(SysUserDetail::getSex, vo.getSex());
        wrapper.set(SysUserDetail::getProfile, vo.getProfile());
        wrapper.set(SysUserDetail::getUserSign, vo.getUserSign());
        int update = sysUserDetailMapper.update(new SysUserDetail(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
    }

    /**
     * 修改个人密码
     *
     * @param vo
     */
    @Override
    public void updatePwd(SysUserUpdPwdVo vo) {
        if (vo.getNewPassword().equals(vo.getOldPassword())) {
            throw new MyException("新密码不能与旧密码一样");
        }
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        SysUser sysUser = getUserByUserId(loginUser.getId());
        String sha256 = SaSecureUtil.sha256(vo.getOldPassword());
        if (!sha256.equals(sysUser.getPassword())) {
            throw new MyException("旧密码输入错误");
        }
        String newPwd = SaSecureUtil.sha256(vo.getNewPassword());
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
     * @param vo
     * @return
     */
    @Override
    public IPage<SysUserPageDto> getPage(SysUserPageVo vo) {
        QueryWrapper<SysUser> wrapper = getWrapper(vo);
        vo.setOrderByColumn("su.create_time");
        vo.setIsAsc("desc");
        IPage<SysUserPageDto> sysUserPage = sysUserMapper.getPageWrapper(vo.getPage(false), wrapper);
        for (SysUserPageDto record : sysUserPage.getRecords()) {
            record.setAdmin(CommonConstant.SUPER_ADMIN.equals(record.getId()));
        }
        return sysUserPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @Override
    public List<SysUserPageDto> getAll(SysUserPageVo vo) {
        QueryWrapper<SysUser> wrapper = getWrapper(vo);
        List<SysUserPageDto> list = sysUserMapper.getAll(wrapper);
        for (SysUserPageDto record : list) {
            record.setAdmin(CommonConstant.SUPER_ADMIN.equals(record.getId()));
        }
        return list;
    }

    private QueryWrapper<SysUser> getWrapper(SysUserPageVo vo) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(vo.getDeptId())) {
            List<SysDept> listByParentId = sysDeptService.getListByParentId(vo.getDeptId(), true);
            List<String> ids = MyUtil.joinToList(listByParentId, SysDept::getId);
            wrapper.in(ObjectUtil.isNotEmpty(vo.getDeptId()), "su.dept_id", ids);
        }
        wrapper.like(ObjectUtil.isNotEmpty(vo.getUsername()), "su.username", vo.getUsername());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), "su.status", vo.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getPhone()), "su.phone", vo.getPhone());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getEmail()), "su.email", vo.getEmail());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), "su.remark", vo.getRemark());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getUserType()), "su.user_type", vo.getUserType());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getNickName()), "sud.nick_name", vo.getNickName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRealName()), "sud.real_name", vo.getRealName());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getAge()), "sud.age", vo.getAge());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getSex()), "sud.sex", vo.getSex());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getProfile()), "sud.profile", vo.getProfile());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getUserSign()), "sud.user_sign", vo.getUserSign());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
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
        map.put("roles", sysRoleService.getAll(new SysRolePageVo()));
        map.put("positions", sysPositionService.getAll(new SysPositionPageVo()));
        if (ObjectUtil.isNotEmpty(id)) {
            SysUserPageDto sysUserByPermissions = getSysUserByPermissions(id);
            map.put("user", sysUserByPermissions);
            map.put("roleIds", MyUtil.joinToList(sysUserByPermissions.getRoles(), SysRole::getId));
            map.put("positionIds", MyUtil.joinToList(sysUserByPermissions.getPositions(), SysPosition::getId));
        }
        return map;
    }

    /**
     * 获取权限范围内的该用户，无权限则抛出异常
     *
     * @param id
     * @return
     */
    private SysUserPageDto getSysUserByPermissions(String id) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("su.id", id);
        wrapper.eq("su.is_delete", SystemConstant.IS_DELETE_NO);
        SysUserPageDto info = sysUserMapper.getInfo(wrapper);
        if (ObjectUtil.isEmpty(info)) {
            throw new MyException("你无权限操作该数据");
        }
        info.setAdmin(CommonConstant.SUPER_ADMIN.equals(info.getId()));
        return info;
    }

    /**
     * 修改
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editInfo(SysUserUpdVo vo) {
        String loginUserId = LoginUtil.getLoginUserId();
        if (loginUserId.equals(vo.getId())) {
            throw new MyException("禁止操作自己");
        }
        if (sysUserMapper.isAdmin(SystemConstant.ROLE_ADMIN, vo.getId())) {
            throw new MyException("禁止操作超级管理员");
        }
        SysUserPageDto sysUserByPermissions = getSysUserByPermissions(vo.getId());
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, vo.getId());
        updateWrapper.set(SysUser::getDeptId, vo.getDeptId());
        updateWrapper.set(SysUser::getStatus, vo.getStatus());
        updateWrapper.set(SysUser::getPhone, vo.getPhone());
        updateWrapper.set(SysUser::getEmail, vo.getEmail());
        updateWrapper.set(SysUser::getOpenId, vo.getOpenId());
        updateWrapper.set(SysUser::getApiKey, vo.getApiKey());
        updateWrapper.set(SysUser::getBanToTime, vo.getBanToTime());
        updateWrapper.set(SysUser::getExpireToTime, vo.getExpireToTime());
        updateWrapper.set(SysUser::getUserType, vo.getUserType());
        updateWrapper.set(SysUser::getRemark, vo.getRemark());
        int rows = sysUserMapper.update(new SysUser(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        //修改detail
        LambdaUpdateWrapper<SysUserDetail> updateWrapperDetail = new LambdaUpdateWrapper<>();
        updateWrapperDetail.eq(SysUserDetail::getUserId, vo.getId());
        updateWrapperDetail.set(SysUserDetail::getNickName, vo.getNickName());
        updateWrapperDetail.set(SysUserDetail::getRealName, vo.getRealName());
        updateWrapperDetail.set(SysUserDetail::getAge, vo.getAge());
        updateWrapperDetail.set(SysUserDetail::getSex, vo.getSex());
        updateWrapperDetail.set(SysUserDetail::getProfile, vo.getProfile());
        updateWrapperDetail.set(SysUserDetail::getUserSign, vo.getUserSign());
        updateWrapperDetail.set(SysUserDetail::getMoney, vo.getMoney());
        int rowsDetail = sysUserDetailMapper.update(new SysUserDetail(), updateWrapperDetail);
        if (rowsDetail < 1) {
            throw new MyException("修改失败");
        }
        //修改关联数据
        sysRoleUserService.handleUserRole(vo.getId(), vo.getRoleIds());
        sysPositionUserService.handleUserPosition(vo.getId(), vo.getPositionIds());
        //注销该账户的登录
        LoginUtil.logoutByUsername(sysUserByPermissions.getUsername());
    }

    /**
     * 新增
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addInfo(SysUserAddVo vo) {
        if (vo.getUsername().contains("@")) {
            throw new MyException("账号不能包含@符号");
        }
        if (StringUtils.isNotBlank(vo.getPhone())) {
            if (checkUserByPhone(vo.getPhone(), null)) {
                throw new MyException("手机号已存在");
            }
        }
        if (StringUtils.isNotBlank(vo.getEmail())) {
            if (checkUserByEmail(vo.getEmail(), null)) {
                throw new MyException("邮箱已存在");
            }
        }
        if (StringUtils.isNotBlank(vo.getOpenId())) {
            if (checkUserByOpenId(vo.getOpenId(), null)) {
                throw new MyException("openId已存在");
            }
        }
        if (StringUtils.isNotBlank(vo.getApiKey())) {
            if (checkUserByApiKey(vo.getApiKey())) {
                throw new MyException("apiKey已存在");
            }
        }
        SysUser userByUsername = getUserByUsername(vo.getUsername());
        if (ObjectUtil.isNotEmpty(userByUsername)) {
            throw new MyException("用户账号已存在");
        }
        SysUser sysUser = new SysUser();
        sysUser.setDeptId(vo.getDeptId());
        sysUser.setUsername(vo.getUsername());
        String newPwd = SaSecureUtil.sha256(vo.getPassword());
        sysUser.setPassword(newPwd);
        sysUser.setStatus(vo.getStatus());
        sysUser.setPhone(vo.getPhone());
        sysUser.setEmail(vo.getEmail());
        sysUser.setOpenId(vo.getOpenId());
        sysUser.setApiKey(vo.getApiKey());
        sysUser.setBanToTime(vo.getBanToTime());
        sysUser.setExpireToTime(vo.getExpireToTime());
        sysUser.setRemark(vo.getRemark());
        sysUser.setUserType(vo.getUserType());
        int rows = sysUserMapper.insert(sysUser);
        if (rows < 1) {
            throw new MyException("新增失败");
        }
        SysUserDetail sysUserDetail = new SysUserDetail();
        sysUserDetail.setUserId(sysUser.getId());
        sysUserDetail.setNickName(vo.getNickName());
        sysUserDetail.setRealName(vo.getRealName());
        sysUserDetail.setAge(vo.getAge());
        sysUserDetail.setSex(vo.getSex());
        sysUserDetail.setProfile(vo.getProfile());
        sysUserDetail.setUserSign(vo.getUserSign());
        sysUserDetail.setAvatar(vo.getAvatar());
        sysUserDetail.setMoney(vo.getMoney());
        int rowsDetail = sysUserDetailMapper.insert(sysUserDetail);
        if (rowsDetail < 1) {
            throw new MyException("新增失败");
        }
        //新增关联数据
        sysRoleUserService.handleUserRole(sysUser.getId(), vo.getRoleIds());
        sysPositionUserService.handleUserPosition(sysUser.getId(), vo.getPositionIds());
    }

    /**
     * 修改状态
     *
     * @param vo
     */
    @Override
    public void changeStatus(ComStatusUpdVo vo) {
        String loginUserId = LoginUtil.getLoginUserId();
        if (loginUserId.equals(vo.getId())) {
            throw new MyException("禁止操作自己");
        }
        if (sysUserMapper.isAdmin(SystemConstant.ROLE_ADMIN, vo.getId())) {
            throw new MyException("禁止操作超级管理员");
        }
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, vo.getId());
        updateWrapper.set(SysUser::getStatus, vo.getStatus());
        int rows = sysUserMapper.update(new SysUser(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
    }

    /**
     * 重置用户密码
     *
     * @param vo
     */
    @Override
    public void resetPwd(SysUserResetPwdVo vo) {
        String loginUserId = LoginUtil.getLoginUserId();
        if (loginUserId.equals(vo.getId())) {
            throw new MyException("禁止操作自己");
        }
        if (sysUserMapper.isAdmin(SystemConstant.ROLE_ADMIN, vo.getId())) {
            throw new MyException("禁止操作超级管理员");
        }
        SysUserPageDto sysUserByPermissions = getSysUserByPermissions(vo.getId());
        String newPwd = SaSecureUtil.sha256(vo.getPassword());
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, vo.getId());
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
        String loginUserId = LoginUtil.getLoginUserId();
        if (ids.contains(loginUserId)) {
            throw new MyException("不能删除自己");
        }
        if (sysUserMapper.isHasAdmin(SystemConstant.ROLE_ADMIN, ids)) {
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
            sysUserMapper.deleteBatchIds(realList);
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
        List<SysRole> sysRoleList = sysRoleService.getAll(new SysRolePageVo());
        SysUserPageDto sysUserByPermissions = getSysUserByPermissions(id);
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
     * @param vo
     */
    @Override
    public void saveAuthRole(SysUserUpdAuthRoleVo vo) {
        String loginUserId = LoginUtil.getLoginUserId();
        if (loginUserId.equals(vo.getUserId())) {
            throw new MyException("禁止操作自己");
        }
        if (sysUserMapper.isAdmin(SystemConstant.ROLE_ADMIN, vo.getUserId())) {
            throw new MyException("禁止操作超级管理员");
        }
        SysUserPageDto sysUserByPermissions = getSysUserByPermissions(vo.getUserId());
        //修改关联数据
        sysRoleUserService.handleUserRole(vo.getUserId(), vo.getRoleIds());
        //注销该账户的登录
        LoginUtil.logoutByUsername(sysUserByPermissions.getUsername());
    }

    /**
     * 查询已分配该角色的用户列表
     *
     * @param vo
     * @param roleId
     * @return
     */
    @Override
    public IPage<SysUserPageDto> getUserListByRoleId(SysUserPageVo vo, String roleId) {
        QueryWrapper<SysUser> wrapper = getWrapper(vo);
        wrapper.exists("SELECT * FROM sys_role_user sur WHERE sur.role_id = '" + roleId + "' AND sur.user_id = su.id");
        IPage<SysUserPageDto> sysUserPage = sysUserMapper.getPageWrapper(vo.getPage(true), wrapper);
        for (SysUserPageDto record : sysUserPage.getRecords()) {
            record.setAdmin(CommonConstant.SUPER_ADMIN.equals(record.getId()));
        }
        return sysUserPage;
    }

    /**
     * 查询未分配该角色的用户列表
     *
     * @param vo
     * @param roleId
     * @return
     */
    @Override
    public IPage<SysUserPageDto> getUserListByUnRoleId(SysUserPageVo vo, String roleId) {
        QueryWrapper<SysUser> wrapper = getWrapper(vo);
        wrapper.notExists("SELECT * FROM sys_role_user sur WHERE sur.role_id = '" + roleId + "' AND sur.user_id = su.id");
        IPage<SysUserPageDto> sysUserPage = sysUserMapper.getPageWrapper(vo.getPage(true), wrapper);
        for (SysUserPageDto record : sysUserPage.getRecords()) {
            record.setAdmin(CommonConstant.SUPER_ADMIN.equals(record.getId()));
        }
        return sysUserPage;
    }

    /**
     * 查询已分配该岗位的用户列表
     *
     * @param vo
     * @param positionId
     * @return
     */
    @Override
    public IPage<SysUserPageDto> getUserListByPositionId(SysUserPageVo vo, String positionId) {
        QueryWrapper<SysUser> wrapper = getWrapper(vo);
        wrapper.exists("SELECT * FROM sys_position_user spr WHERE spr.position_id = '" + positionId + "' AND spr.user_id = su.id");
        IPage<SysUserPageDto> sysUserPage = sysUserMapper.getPageWrapper(vo.getPage(true), wrapper);
        for (SysUserPageDto record : sysUserPage.getRecords()) {
            record.setAdmin(CommonConstant.SUPER_ADMIN.equals(record.getId()));
        }
        return sysUserPage;
    }

    /**
     * 查询未分配该岗位的用户列表
     *
     * @param vo
     * @param positionId
     * @return
     */
    @Override
    public IPage<SysUserPageDto> getUserListByUnPositionId(SysUserPageVo vo, String positionId) {
        QueryWrapper<SysUser> wrapper = getWrapper(vo);
        wrapper.notExists("SELECT * FROM sys_position_user spr WHERE spr.position_id = '" + positionId + "' AND spr.user_id = su.id");
        IPage<SysUserPageDto> sysUserPage = sysUserMapper.getPageWrapper(vo.getPage(true), wrapper);
        for (SysUserPageDto record : sysUserPage.getRecords()) {
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
    public List<SysUserPageDto> getUserList(String id, String username, String nickName, String realName, String phone, String email) {
        List<SysUserPageDto> list = new ArrayList<>();
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
        for (SysUserPageDto record : list) {
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
     * @param vo
     * @return
     */
    @Override
    public Result getEmailValidCode(SysUserUpdBindVo vo) {
        captchaService.validatedCaptcha(vo);
        if (ObjectUtil.isEmpty(vo.getEmail())) {
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
        Boolean exist = checkUserByEmail(vo.getEmail(), loginUser.getId());
        if (exist) {
            throw new MyException("该邮箱已经被其他账户绑定");
        }
        if (vo.getEmail().equals(loginUser.getEmail())) {
            throw new MyException("当前账户已经绑定了此邮箱");
        }
        String value = (String) RedisUtil.getValue(RedisConstant.VALIDA_EMAIL + loginUser.getId() + ":" + vo.getEmail());
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
        Boolean aBoolean = EmailUtil.sendEmail(vo.getEmail(), subject, content, true, null);
        if (!aBoolean) {
            throw new MyException("发送邮件失败，请联系管理员");
        }
        RedisUtil.setValue(RedisConstant.VALIDA_EMAIL + loginUser.getId() + ":" + vo.getEmail(), code, keepLive, TimeUnit.SECONDS);
        Integer waitTime = EmailUtil.saveSendByUserId(loginUser.getId(), sysSendLimitConfigVo);
        Map<String, Object> map = new HashMap<>();
        map.put("waitTime", waitTime);
        map.put("msg", "验证码已发送，" + keepLiveStr + "有效");
        return Result.ok(map);
    }

    /**
     * 获取手机验证码
     *
     * @param vo
     * @return
     */
    @Override
    public Result getPhoneValidCode(SysUserUpdBindVo vo) {
        captchaService.validatedCaptcha(vo);
        if (ObjectUtil.isEmpty(vo.getPhone())) {
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
        Boolean exist = checkUserByPhone(vo.getPhone(), loginUser.getId());
        if (exist) {
            throw new MyException("该手机号已经被其他账户绑定");
        }
        if (vo.getPhone().equals(loginUser.getPhone())) {
            throw new MyException("当前账户已经绑定了此手机号");
        }
        String value = (String) RedisUtil.getValue(RedisConstant.VALIDA_PHONE + loginUser.getId() + ":" + vo.getPhone());
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
        SmsSendResult smsSendResult = SmsUtil.sendSms(vo.getPhone(), sysSmsTemplateConfigVo.getBindPhone().getTemplateId(), smsMap);
        if (!smsSendResult.isSuccess()) {
            throw new MyException("发送短信失败，请联系管理员");
        }
        RedisUtil.setValue(RedisConstant.VALIDA_PHONE + loginUser.getId() + ":" + vo.getPhone(), code, keepLive, TimeUnit.SECONDS);
        Integer waitTime = SmsUtil.saveSendByUserId(loginUser.getId(), sysSendLimitConfigVo);
        Map<String, Object> map = new HashMap<>();
        map.put("waitTime", waitTime);
        map.put("msg", "验证码已发送，" + keepLiveStr + "有效");
        return Result.ok(map);
    }

    /**
     * 修改邮箱绑定
     *
     * @param vo
     * @return
     */
    @Override
    public Result updateBindEmail(SysUserUpdBindVo vo) {
        if (ObjectUtil.isEmpty(vo.getEmail())) {
            throw new MyException("请填写邮箱");
        }
        if (ObjectUtil.isEmpty(vo.getValidCode())) {
            throw new MyException("请填写收到的邮箱验证码");
        }
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        String validCode = (String) RedisUtil.getValue(RedisConstant.VALIDA_EMAIL + loginUser.getId() + ":" + vo.getEmail());
        if (ObjectUtil.isEmpty(validCode)) {
            throw new MyException("邮箱验证码错误或者已失效");
        }
        if (!vo.getValidCode().equals(validCode)) {
            throw new MyException("邮箱验证码错误，请检查");
        }
        RedisUtil.del(RedisConstant.VALIDA_EMAIL + loginUser.getId() + ":" + vo.getEmail());
        Boolean exist = checkUserByEmail(vo.getEmail(), loginUser.getId());
        if (exist) {
            throw new MyException("该邮箱已经被其他账户绑定");
        }
        if (vo.getEmail().equals(loginUser.getEmail())) {
            throw new MyException("当前账户已经绑定了此邮箱");
        }
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getId, loginUser.getId());
        wrapper.set(SysUser::getEmail, vo.getEmail());
        int update = sysUserMapper.update(new SysUser(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        LoginUtil.logout();
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "绑定成功，请重新登录");
        return Result.ok(map);
    }

    /**
     * 修改手机绑定
     *
     * @param vo
     * @return
     */
    @Override
    public Result updateBindPhone(SysUserUpdBindVo vo) {
        if (ObjectUtil.isEmpty(vo.getPhone())) {
            throw new MyException("请填写手机号");
        }
        if (ObjectUtil.isEmpty(vo.getValidCode())) {
            throw new MyException("请填写收到的短信验证码");
        }
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        String validCode = (String) RedisUtil.getValue(RedisConstant.VALIDA_PHONE + loginUser.getId() + ":" + vo.getPhone());
        if (ObjectUtil.isEmpty(validCode)) {
            throw new MyException("短信验证码错误或者已失效");
        }
        if (!vo.getValidCode().equals(validCode)) {
            throw new MyException("短信验证码错误，请检查");
        }
        RedisUtil.del(RedisConstant.VALIDA_PHONE + loginUser.getId() + ":" + vo.getPhone());
        Boolean exist = checkUserByPhone(vo.getPhone(), loginUser.getId());
        if (exist) {
            throw new MyException("该手机号已经被其他账户绑定");
        }
        if (vo.getPhone().equals(loginUser.getPhone())) {
            throw new MyException("当前账户已经绑定了此手机号");
        }
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getId, loginUser.getId());
        wrapper.set(SysUser::getPhone, vo.getPhone());
        int update = sysUserMapper.update(new SysUser(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        LoginUtil.logout();
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "绑定成功，请重新登录");
        return Result.ok(map);
    }
}
