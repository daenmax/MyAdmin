package cn.daenx.system.domain.dto;

import cn.daenx.framework.common.vo.BaseDto;
import cn.daenx.framework.excel.ExcelConverter;
import cn.daenx.framework.serializer.annotation.Dict;
import cn.daenx.system.domain.po.SysDept;
import cn.daenx.system.domain.po.SysPosition;
import cn.daenx.system.domain.po.SysRole;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
//导出时忽略没有@ExcelProperty的字段
@ExcelIgnoreUnannotated
public class SysUserPageDto extends BaseDto {

    @ExcelProperty(value = "用户Uid")
    private String id;

    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号")
    private String username;

    /**
     * 账号状态，0=正常，1=停用
     */
    @ExcelProperty(value = "账号状态", converter = ExcelConverter.class)
    @Dict(dictCode = "sys_normal_disable", custom = {})
    private String status;

    /**
     * 用户手机号
     */
    @ExcelProperty(value = "用户手机号")
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 微信open_id
     */
    @ExcelProperty(value = "微信openId")
    private String openId;

    /**
     * 开放API key
     */
    @ExcelProperty(value = "开放API key")
    private String apiKey;

    /**
     * 锁定结束时间
     */
    @ExcelProperty(value = "锁定结束时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime banToTime;

    /**
     * 到期时间，null则永不过期
     */
    @ExcelProperty(value = "到期时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime expireToTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 用户类型，具体看字典
     */
    @ExcelProperty(value = "用户类型", converter = ExcelConverter.class)
    @Dict(dictCode = "sys_user_type", custom = {})
    private String userType;
    private String userTypeName;


    /**
     * 用户昵称
     */
    @ExcelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 真实姓名
     */
    @ExcelProperty(value = "真实姓名")
    private String realName;

    /**
     * 年龄
     */
    @ExcelProperty(value = "年龄")
    private String age;

    /**
     * 性别，0=女，1=男，2=未知
     */
    @ExcelProperty(value = "性别", converter = ExcelConverter.class)
    @Dict(dictCode = "sys_user_sex", custom = {})
    private String sex;

    /**
     * 个人简介
     */
    @ExcelProperty(value = "个人简介")
    private String profile;

    /**
     * 个性签名
     */
    @ExcelProperty(value = "个性签名")
    private String userSign;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private String avatar;

    /**
     * 账户余额，单位分
     */
    @ExcelProperty(value = "账户余额，单位分")
    private Integer money;


    /**
     * 用户角色列表
     */
    private List<SysRole> roles;

    /**
     * 用户岗位列表
     */
    private List<SysPosition> positions;

    /**
     * 用户部门列表
     */
    private List<SysDept> depts;

    /**
     * 是否是管理员
     */
    private Boolean admin;
}
