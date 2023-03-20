package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.Constant;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.LoginUtil;
import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysPosition;
import cn.daenx.myadmin.system.po.SysRole;
import cn.daenx.myadmin.system.po.SysUserDetail;
import cn.daenx.myadmin.system.service.*;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.daenx.myadmin.system.vo.SysUserVo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.mapper.SysUserMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserDetailService sysUserDetailService;
    @Resource
    private SysRoleUserService sysRoleUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysPositionService sysPositionService;


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
    public SysUserVo getUserInfoByUserId(String userId) {
        SysUserVo userInfoByUserId = sysUserMapper.getUserInfoByUserId(userId);
        if (userInfoByUserId != null) {
            userInfoByUserId.setAdmin(SystemConstant.IS_ADMIN_ID.equals(userInfoByUserId.getId()));
        }
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
                    throw new MyException("账号已到期");
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
        //创建用户、角色关联
        Boolean role = sysRoleUserService.createRoleUser(roleId, sysUser.getId());
        if (!role) {
            throw new MyException("创建用户角色失败");
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
        SysUserVo sysUserVo = getUserInfoByUserId(loginUser.getId());
        if (sysUserVo == null) {
            throw new MyException("用户不存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user", sysUserVo);
        List<SysRole> roleList = sysRoleService.getSysRoleListByUserId(sysUserVo.getId());
        List<SysPosition> positionList = sysPositionService.getSysPositionListByUserId(sysUserVo.getId());
        map.put("roleGroup", MyUtil.join(roleList, SysRole::getName, ","));
        map.put("postGroup", MyUtil.join(positionList, SysPosition::getName, ","));
        return map;
    }
}
