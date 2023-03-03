package cn.daenx.myadmin.system.vo;

import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysPosition;
import cn.daenx.myadmin.system.po.SysRole;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserVo {
    private String id;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 年龄
     */
    private String age;

    /**
     * 性别，0=女，1=男，2=未知
     */
    private String sex;

    /**
     * 个人简介
     */
    private String profile;

    /**
     * 个性签名
     */
    private String userSign;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账户余额
     */
    private Integer money;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 账号状态，0=正常，1=停用，2=注销
     */
    private Integer status;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 微信open_id
     */
    private String openId;

    /**
     * 锁定结束时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime banToTime;

    /**
     * 到期时间，null则永不过期
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime expireToTime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 用户类型ID
     */
    private String userTypeId;

    /**
     * 用户类型名称
     */
    private String userTypeName;

    /**
     * 创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 创建人账号
     */
    private String createName;

    /**
     * 创建人ID
     */
    private String createId;

    /**
     * 修改人账号
     */
    private String updateName;

    /**
     * 修改人ID
     */
    private String updateId;

    /**
     * 用户角色列表
     */
    private List<SysRole> roles;

    /**
     * 用户岗位列表
     */
    private List<SysPosition> positions;

    /**
     * 是否是管理员
     */
    private Boolean admin;
}
