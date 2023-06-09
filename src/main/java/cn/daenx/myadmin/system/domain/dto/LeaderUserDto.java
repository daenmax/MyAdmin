package cn.daenx.myadmin.system.domain.dto;

import cn.daenx.myadmin.common.vo.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class LeaderUserDto extends BaseDto {

    private String id;

    /**
     * 部门ID
     */
    private String deptId;
    private String deptName;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 账号状态，0=正常，1=停用
     */
    private String status;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

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
    private String remark;

    /**
     * 用户类型，具体看字典
     */
    private String userType;
    private String userTypeName;


    /**
     * 用户昵称
     */
    private String nickName;

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
     * 账户余额，单位分
     */
    private Integer money;

    /**
     * 是否是管理员
     */
    private Boolean admin;
}
