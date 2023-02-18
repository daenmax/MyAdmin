package cn.daenx.myadmin.system.po;

import cn.daenx.myadmin.common.vo.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户信息表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user_detail")
public class SysUserDetail extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 关联用户ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 用户昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 年龄
     */
    @TableField(value = "age")
    private String age;

    /**
     * 性别，0=女，1=男
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 个人简介
     */
    @TableField(value = "profile")
    private String profile;

    /**
     * 个性签名
     */
    @TableField(value = "user_sign")
    private String userSign;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;
}