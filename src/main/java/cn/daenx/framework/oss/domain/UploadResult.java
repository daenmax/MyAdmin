package cn.daenx.framework.oss.domain;

import lombok.Builder;
import lombok.Data;

/**
 * 上传结果信息
 */
@Data
@Builder
public class UploadResult {

    /**
     * 文件路径
     * 例如：http://127.0.0.1:9000/test/MyAdmin/2023/04/16/d85ce625e0a34568938c218554938d34.jpg
     */
    private String fileUrl;

    /**
     * 文件名
     * 包含路径，例如：MyAdmin/2023/04/16/d85ce625e0a34568938c218554938d34.jpg
     */
    private String fileName;

    /**
     * 原始文件名称
     * 例如：大恩的头像.jpg
     */
    private String originalName;

    /**
     * MD5
     * 通常就是eTag，例如：4879054b23eb68d156eb7d92906aa113
     */
    private String fileMd5;

    /**
     * 后缀
     * 例如：.jpg
     */
    private String fileSuffix;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件类型
     * 例如：image/jpeg
     */
    private String fileType;

    /**
     * 系统文件表里的ID
     */
    private String sysFileId;

    /**
     * 是否已经存在
     */
    private Boolean isExist;
}
