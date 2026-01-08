package cn.daenx.data.system.domain.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysOssConfigAddVo {

    /**
     * 配置名称
     * 例如：minio、阿里云、腾讯云、七牛云、京东云、华为云
     */
    @NotBlank(message = "配置名称不能为空")
    private String name;

    /**
     * accessKey
     */
    @NotBlank(message = "accessKey不能为空")
    private String accessKey;

    /**
     * secretKey
     */
    @NotBlank(message = "secretKey不能为空")
    private String secretKey;

    /**
     * 存储桶名称
     */
    @NotBlank(message = "存储桶名称不能为空")
    private String bucketName;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 访问站点endpoint
     */
    @NotBlank(message = "访问站点endpoint不能为空")
    private String endpoint;

    /**
     * 自定义域名
     */
    private String domain;

    /**
     * 是否https，0=否，1=是
     */
    private String isHttps;

    /**
     * 地域
     */
    private String region;

    /**
     * 存储桶权限类型，0=private，1=public，2=custom
     */
    @NotBlank(message = "存储桶权限类型不能为空")
    private String accessPolicy;

    /**
     * 配置状态，0=正常，1=禁用
     */
    @NotBlank(message = "配置状态不能为空")
    private String status;

    /**
     * 链接访问有效期，当存储桶为private时有效，单位秒
     */
    @NotNull(message = "链接有效期不能为空")
    private Integer urlValidAccessTime;

    /**
     * 链接缓存有效期，单位秒，0=不缓存
     */
    @NotNull(message = "链接有效期缓存不能为空")
    private Integer urlValidCacheTime;

    /**
     * 备注
     */
    private String remark;
}
