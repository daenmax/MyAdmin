package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.common.vo.ComStatusUpdVo;
import cn.daenx.myadmin.system.dto.SysUserPageDto;
import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.vo.SysUserPageVo;
import cn.daenx.myadmin.system.vo.SysUserUpdInfoVo;
import cn.daenx.myadmin.system.vo.SysUserUpdPwdVo;
import cn.daenx.myadmin.system.vo.SysUserUpdVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SysUserService extends IService<SysUser> {

    /**
     * 通过账号获取用户
     *
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);

    /**
     * 通过ID获取用户
     *
     * @param userId
     * @return
     */
    SysUser getUserByUserId(String userId);

    /**
     * 通过ID获取用户信息
     *
     * @param userId
     * @return
     */
    SysUserPageDto getUserInfoByUserId(String userId);

    /**
     * 校验用户状态是否正常
     *
     * @param sysUser
     * @return
     */
    Boolean validatedUser(SysUser sysUser);

    /**
     * 注册用户
     *
     * @param sysUser
     * @param roleId
     * @return
     */
    Boolean registerUser(SysUser sysUser, String roleId);

    /**
     * 个人信息
     *
     * @return
     */
    Map<String, Object> profile();

    /**
     * 修改个人资料
     *
     * @param vo
     */
    void updInfo(SysUserUpdInfoVo vo);

    /**
     * 修改个人密码
     *
     * @param vo
     */
    void updatePwd(SysUserUpdPwdVo vo);

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysUserPageDto> getPage(SysUserPageVo vo);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    Map<String, Object> getInfo(String id);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(SysUserUpdVo vo);

    /**
     * 修改状态
     *
     * @param vo
     */
    void changeStatus(ComStatusUpdVo vo);
}
