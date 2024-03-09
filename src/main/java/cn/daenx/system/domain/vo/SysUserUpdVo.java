package cn.daenx.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SysUserUpdVo {
    //----------------------- user 开始
    @NotBlank(message = "ID不能为空")
    private String id;

    /**
     * 账号状态，0=正常，1=停用
     */
    @NotBlank(message = "账号状态不能为空")
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
     * 微信open_id
     */
    private String openId;

    /**
     * 开放API key
     */
    private String apiKey;

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
     * 用户类型，具体看字典
     */
    @NotBlank(message = "用户类型不能为空")
    private String userType;

    /**
     * 备注
     */
    private String remark;

    //----------------------- userDetail 开始
    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
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
    @NotBlank(message = "用户性别不能为空")
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

    //----------------------- 关联数据 开始
    @Size(min = 1, message = "最少选择一个角色")
    private List<String> roleIds;
    @Size(min = 1, message = "最少选择一个部门")
    private List<String> deptIds;
    private List<String> positionIds;
}
