package cn.daenx.system.domain.po;

import cn.daenx.framework.common.vo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
    * OSS文件表
    */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_file")
public class SysFile extends BaseEntity implements Serializable {

    /**
     * 原始文件名称
     * 例如：大恩的头像.jpg
     */
    @TableField(value = "original_name")
    private String originalName;

    /**
     * 文件名称
     * 例如：MyAdmin/2023/04/16/63a15d0444cc49b7ae9aed077b88dc4d.jpg
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 文件后缀名
     * 例如：.jpg
     */
    @TableField(value = "file_suffix")
    private String fileSuffix;

    /**
     * 文件URL地址
     * 例如：http://127.0.0.1:9000/test/MyAdmin/2023/04/16/63a15d0444cc49b7ae9aed077b88dc4d.jpg
     */
    @TableField(value = "file_url")
    private String fileUrl;

    /**
     * 文件大小
     * 单位字节
     */
    @TableField(value = "file_size")
    private Long fileSize;

    /**
     * 文件MD5（eTag）
     */
    @TableField(value = "file_md5")
    private String fileMd5;

    /**
     * 文件类型，例如：例如：image/jpeg
     */
    @TableField(value = "file_type")
    private String fileType;

    /**
     * 所属OSS配置ID
     */
    @TableField(value = "oss_id")
    private String ossId;

    /**
     * 文件状态，0=正常，1=禁用
     */
    @TableField(value = "status")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}
