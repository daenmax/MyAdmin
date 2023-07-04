package cn.daenx.myadmin.system.domain.po;

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
    * OSS配置表
    */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_oss_config")
public class SysOssConfig extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 配置名称
     * 例如：minio、阿里云、腾讯云、七牛云、京东云、华为云
     */
    @TableField(value = "name")
    private String name;

    /**
     * accessKey
     */
    @TableField(value = "access_key")
    private String accessKey;

    /**
     * secretKey
     */
    @TableField(value = "secret_key")
    private String secretKey;

    /**
     * 存储桶名称
     */
    @TableField(value = "bucket_name")
    private String bucketName;

    /**
     * 前缀
     */
    @TableField(value = "prefix")
    private String prefix;

    /**
     * 访问站点endpoint
     */
    @TableField(value = "endpoint")
    private String endpoint;

    /**
     * 自定义域名
     */
    @TableField(value = "domain")
    private String domain;

    /**
     * 是否https，0=否，1=是
     */
    @TableField(value = "is_https")
    private String isHttps;

    /**
     * 地域
     */
    @TableField(value = "region")
    private String region;

    /**
     * 存储桶权限类型，0=private，1=public，2=custom
     */
    @TableField(value = "access_policy")
    private String accessPolicy;

    /**
     * 正在使用，0=否，1=是（在所有数据中，只有一条数据可是正在使用）
     */
    @TableField(value = "in_use")
    private String inUse;

    /**
     * 配置状态，0=正常，1=禁用
     */
    @TableField(value = "status")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
}
