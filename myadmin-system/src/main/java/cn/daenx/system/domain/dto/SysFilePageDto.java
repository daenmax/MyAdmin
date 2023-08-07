package cn.daenx.system.domain.dto;

import cn.daenx.framework.common.vo.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class SysFilePageDto extends BaseDto {
    private String id;

    /**
     * 原始文件名称
     * 例如：大恩的头像.jpg
     */
    private String originalName;

    /**
     * 文件名称
     * 例如：MyAdmin/2023/04/16/63a15d0444cc49b7ae9aed077b88dc4d.jpg
     */
    private String fileName;

    /**
     * 文件后缀名
     * 例如：.jpg
     */
    private String fileSuffix;

    /**
     * 文件URL地址
     * 例如：http://127.0.0.1:9000/test/MyAdmin/2023/04/16/63a15d0444cc49b7ae9aed077b88dc4d.jpg
     */
    private String fileUrl;

    /**
     * 文件大小
     * 单位字节
     */
    private Long fileSize;

    /**
     * 文件MD5（eTag）
     */
    private String fileMd5;

    /**
     * 文件类型，例如：例如：image/jpeg
     */
    private String fileType;

    /**
     * 所属OSS配置ID
     */
    private String ossId;

    /**
     * 文件状态，0=正常，1=禁用
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所属OSS配置名称
     */
    private String ossName;
}
