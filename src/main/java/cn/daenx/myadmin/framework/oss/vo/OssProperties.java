package cn.daenx.myadmin.framework.oss.vo;

import lombok.Data;

/**
 * OSS配置信息
 */
@Data
public class OssProperties {

    /**
     * 配置ID
     */
    private String id;

    /**
     * 配置名称
     */
    private String name;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 访问站点endpoint
     */
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
    private String accessPolicy;

}
