package cn.daenx.myadmin.system.domain.vo;

import cn.daenx.myadmin.common.vo.BasePageVo;
import lombok.Data;

@Data
public class SysOssConfigPageVo extends BasePageVo {

    /**
     * 配置名称
     * 例如：minio、阿里云、腾讯云、七牛云、京东云、华为云
     */
    private String name;

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

    /**
     * 正在使用，0=否，1=是（在所有数据中，只有一条数据可是正在使用）
     */
    private String inUse;

    /**
     * 配置状态，0=正常，1=禁用
     */
    private String status;

    /**
     * 备注
     */
    private String remark;
}
