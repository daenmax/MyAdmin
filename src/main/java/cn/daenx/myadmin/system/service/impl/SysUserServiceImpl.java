package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.common.constant.Constant;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.LoginUtil;
import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.common.vo.ComStatusUpdVo;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.dto.SysUserPageDto;
import cn.daenx.myadmin.system.mapper.SysPositionMapper;
import cn.daenx.myadmin.system.mapper.SysUserDetailMapper;
import cn.daenx.myadmin.system.po.*;
import cn.daenx.myadmin.system.service.*;
import cn.daenx.myadmin.system.vo.*;
import cn.daenx.myadmin.test.po.TestData;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysUserMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * 通过手机号检查用户是否存在
     *
     * @param phone
     * @return
     */
    @Override
    public Boolean checkUserByPhone(String phone) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getPhone, phone);
        wrapper.eq(SysUser::getStatus, SystemConstant.STATUS_NORMAL);
        return sysUserMapper.exists(wrapper);
    }

    /**
     * 通过邮箱检查用户是否存在
     *
     * @param email
     * @return
     */
    @Override
    public Boolean checkUserByEmail(String email) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getEmail, email);
        wrapper.eq(SysUser::getStatus, SystemConstant.STATUS_NORMAL);
        return sysUserMapper.exists(wrapper);
    }

    /**
     * 通过openId检查用户是否存在
     *
     * @param openId
     * @return
     */
    @Override
    public Boolean checkUserByOpenId(String openId) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getOpenId, openId);
        wrapper.eq(SysUser::getStatus, SystemConstant.STATUS_NORMAL);
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
        wrapper.eq(SysUser::getStatus, SystemConstant.STATUS_NORMAL);
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
                String banToTime = LocalDateTimeUtil.format(sysUser.getBanToTime(), Constant.DATE_TIME_FORMAT);
                throw new MyException("账号被锁定，自动解除时间：" + banToTime);
            }
        }
        if (SystemConstant.USER_STATUS_NORMAL.equals(sysUser.getStatus())) {
            //正常，需要判断是否到期
            if (ObjectUtil.isEmpty(sysUser.getExpireToTime())) {
                return true;
            } else {
                if (LocalDateTime.now().isAfter(sysUser.getExpireToTime())) {
                    String expireToTime = LocalDateTimeUtil.format(sysUser.getExpireToTime(), Constant.DATE_TIME_FORMAT);
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
     * @param roleId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean registerUser(SysUser sysUser, String roleId) {
        sysUserMapper.insert(sysUser);
        //创建用户、详细信息关联
        SysUserDetail sysUserDetail = new SysUserDetail();
        sysUserDetail.setUserId(sysUser.getId());
        Boolean detail = sysUserDetailService.createDetail(sysUserDetail);
        if (!detail) {
            throw new MyException("创建用户信息失败");
        }
        List<String> roleIds = new ArrayList<>();
        roleIds.add(roleId);
        //创建用户、角色关联
        sysRoleUserService.handleUserRole(sysUser.getId(), roleIds);
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
        int update = sysUserDetailMapper.update(null, wrapper);
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
        int update = sysUserMapper.update(null, wrapper);
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
        IPage<SysUserPageDto> sysUserPage = sysUserMapper.getPageWrapper(vo.getPage(true), wrapper);
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
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (vo != null) {
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
        }
        wrapper.eq("su.is_delete", SystemConstant.IS_DELETE_NO);
        List<SysUserPageDto> list = sysUserMapper.getAll(wrapper);
        return list;
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
        map.put("roles", sysRoleService.getSysRoleList());
        map.put("positions", sysPositionService.getSysPositionList());
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
            throw new MyException("你无权限修改该数据");
        }
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
        if (SystemConstant.IS_ADMIN_ID.equals(vo.getId())) {
            throw new MyException("禁止操作管理员");
        }
        String userId = LoginUtil.getLoginUserId();
        if (userId.equals(vo.getId())) {
            throw new MyException("禁止操作自己");
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
        int rows = sysUserMapper.update(null, updateWrapper);
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
        int rowsDetail = sysUserDetailMapper.update(null, updateWrapperDetail);
        if (rowsDetail < 1) {
            throw new MyException("修改失败");
        }
        //修改关联数据
        sysRoleUserService.handleUserRole(vo.getId(), vo.getRoleIds());
        sysPositionUserService.handleUserPosition(vo.getId(), vo.getPositionIds());
        //注销该账户的登录
        LoginUtil.logout(sysUserByPermissions.getUsername());
    }

    /**
     * 新增
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addInfo(SysUserAddVo vo) {
        if (StringUtils.isNotBlank(vo.getPhone())) {
            if (checkUserByPhone(vo.getPhone())) {
                throw new MyException("手机号已存在");
            }
        }
        if (StringUtils.isNotBlank(vo.getEmail())) {
            if (checkUserByEmail(vo.getEmail())) {
                throw new MyException("邮箱已存在");
            }
        }
        if (StringUtils.isNotBlank(vo.getOpenId())) {
            if (checkUserByOpenId(vo.getOpenId())) {
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
    @DataScope(alias = "sys_user", field = "id")
    public void changeStatus(ComStatusUpdVo vo) {
        if (SystemConstant.IS_ADMIN_ID.equals(vo.getId())) {
            throw new MyException("禁止操作管理员");
        }
        String userId = LoginUtil.getLoginUserId();
        if (userId.equals(vo.getId())) {
            throw new MyException("禁止操作自己");
        }
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, vo.getId());
        updateWrapper.set(SysUser::getStatus, vo.getStatus());
        int rows = sysUserMapper.update(null, updateWrapper);
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
    @DataScope(alias = "sys_user", field = "id")
    public void resetPwd(SysUserResetPwdVo vo) {
        if (SystemConstant.IS_ADMIN_ID.equals(vo.getId())) {
            throw new MyException("禁止操作管理员");
        }
        String userId = LoginUtil.getLoginUserId();
        if (userId.equals(vo.getId())) {
            throw new MyException("禁止操作自己");
        }
        SysUserPageDto sysUserByPermissions = getSysUserByPermissions(vo.getId());
        String newPwd = SaSecureUtil.sha256(vo.getPassword());
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, vo.getId());
        updateWrapper.set(SysUser::getPassword, newPwd);
        int rows = sysUserMapper.update(null, updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        //注销该账户的登录
        LoginUtil.logout(sysUserByPermissions.getUsername());
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    @DataScope(alias = "sys_user", field = "id")
    public void deleteByIds(String[] ids) {
        List<String> idList = Arrays.asList(ids);
        String userId = LoginUtil.getLoginUserId();
        if (idList.contains(userId)) {
            throw new MyException("不能删除自己");
        }
        if (idList.contains(SystemConstant.IS_ADMIN_ID)) {
            throw new MyException("禁止操作管理员");
        }
        //获取有权限操作的用户
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("su.id", idList);
        List<SysUser> sysUsers = sysUserMapper.getUserList(queryWrapper);
        for (SysUser sysUser : sysUsers) {
            //注销账户的登录
            LoginUtil.logout(sysUser.getUsername());
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
