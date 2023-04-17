package cn.daenx.myadmin.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件和图片上传限制策略
 */
@Data
@AllArgsConstructor
public class SysUploadConfigVo implements Serializable {

    /**
     * 同时上传文件个数
     */
    private Integer limit;

    /**
     * 最大文件尺寸：MB
     */
    private Integer fileSize;
    /**
     * 允许的文件类型
     */
    private String[] fileType;

    /**
     * 是否显示提示
     */
    private Boolean isShowTip;


}
