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
    * OSS文件表
    */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_file")
public class SysFile extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 原始文件名称
     */
    @TableField(value = "original_name")
    private String originalName;

    /**
     * 文件名称
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 文件后缀名
     */
    @TableField(value = "file_suffix")
    private String fileSuffix;

    /**
     * 文件URL地址
     */
    @TableField(value = "file_url")
    private String fileUrl;

    /**
     * 文件大小
     */
    @TableField(value = "file_size")
    private Integer fileSize;

    /**
     * 所属OSS
     */
    @TableField(value = "oss_id")
    private String ossId;

    /**
     * 文件状态，0=正常，1=禁用
     */
    @TableField(value = "`status`")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}