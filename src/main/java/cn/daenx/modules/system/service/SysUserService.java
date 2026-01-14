package cn.daenx.modules.system.service;

import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.modules.system.domain.dto.sysUser.*;
import cn.daenx.modules.system.domain.po.SysUser;
import cn.daenx.modules.system.domain.vo.sysUser.SysUserPageVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {
    /**
     * 通过手机号检查用户是否存在
     *
     * @param phone
     * @param nowId 排除ID
     * @return
     */
    Boolean checkUserByPhone(String phone, String nowId);

    /**
     * 通过邮箱检查用户是否存在
     *
     * @param email
     * @param nowId 排除ID
     * @return
     */
    Boolean checkUserByEmail(String email, String nowId);

    /**
     * 通过openId检查用户是否存在
     *
     * @param openId
     * @param nowId  排除ID
     * @return
     */
    Boolean checkUserByOpenId(String openId, String nowId);

    /**
     * 通过apiKey检查用户是否存在
     *
     * @param apiKey
     * @return
     */
    Boolean checkUserByApiKey(String apiKey);

    /**
     * 通过账号获取用户
     *
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);

    /**
     * 通过邮箱获取用户
     *
     * @param email
     * @return
     */
    SysUser getUserByEmail(String email);

    /**
     * 通过手机获取用户
     *
     * @param phone
     * @return
     */
    SysUser getUserByPhone(String phone);

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
    SysUserPageVo getUserInfoByUserId(String userId);

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
     * @param deptCodes     部门编码
     * @param roleCodes     角色编码
     * @param positionCodes 岗位编码
     * @return
     */
    Boolean registerUser(SysUser sysUser, String[] deptCodes, String[] roleCodes, String[] positionCodes);

    /**
     * 个人信息
     *
     * @return
     */
    Map<String, Object> profile();

    /**
     * 修改个人资料
     *
     * @param dto
     */
    void updInfo(SysUserUpdInfoDto dto);

    /**
     * 修改个人密码
     *
     * @param dto
     */
    void updatePwd(SysUserUpdPwdDto dto);

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    IPage<SysUserPageVo> getPage(SysUserPageDto dto);

    /**
     * 导出
     *
     * @param dto
     * @return
     */
    List<SysUserPageVo> exportData(SysUserPageDto dto);

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
     * @param dto
     */
    void editInfo(SysUserUpdDto dto);

    /**
     * 新增
     *
     * @param dto
     */
    void addInfo(SysUserAddDto dto);

    /**
     * 修改状态
     *
     * @param dto
     */
    void changeStatus(ComStatusUpdDto dto);

    /**
     * 重置用户密码
     *
     * @param dto
     */
    void resetPwd(SysUserResetPwdDto dto);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 根据用户编号获取授权角色
     *
     * @param id
     * @return
     */
    Map<String, Object> authRole(String id);

    /**
     * 保存用户授权角色
     *
     * @param dto
     */
    void saveAuthRole(SysUserUpdAuthRoleDto dto);

    /**
     * 查询已分配该角色的用户列表
     *
     * @param dto
     * @param roleId
     * @return
     */
    IPage<SysUserPageVo> getUserListByRoleId(SysUserPageDto dto, String roleId);

    /**
     * 查询未分配该角色的用户列表
     *
     * @param dto
     * @param roleId
     * @return
     */
    IPage<SysUserPageVo> getUserListByUnRoleId(SysUserPageDto dto, String roleId);

    /**
     * 查询已分配该岗位的用户列表
     *
     * @param dto
     * @param positionId
     * @return
     */
    IPage<SysUserPageVo> getUserListByPositionId(SysUserPageDto dto, String positionId);

    /**
     * 查询未分配该岗位的用户列表
     *
     * @param dto
     * @param positionId
     * @return
     */
    IPage<SysUserPageVo> getUserListByUnPositionId(SysUserPageDto dto, String positionId);

    /**
     * 获取用户列表
     *
     * @param id       如果传ID，则会忽略其他全部参数
     * @param username
     * @param nickName
     * @param realName
     * @param phone
     * @param email
     * @return
     */
    List<SysUserPageVo> getUserList(String id, String username, String nickName, String realName, String phone, String email);

    /**
     * 修改头像
     * 返回头像链接
     *
     * @param file
     * @return
     */
    String avatar(MultipartFile file);

    /**
     * 获取邮箱验证码
     *
     * @param dto
     * @return
     */
    Map<String, Object>  getEmailValidCode(SysUserUpdBindDto dto);

    /**
     * 获取手机验证码
     *
     * @param dto
     * @return
     */
    Map<String, Object>  getPhoneValidCode(SysUserUpdBindDto dto);

    /**
     * 修改邮箱绑定
     *
     * @param dto
     * @return
     */
    Map<String, Object>  updateBindEmail(SysUserUpdBindDto dto);

    /**
     * 修改手机绑定
     *
     * @param dto
     * @return
     */
    Map<String, Object>  updateBindPhone(SysUserUpdBindDto dto);
}
