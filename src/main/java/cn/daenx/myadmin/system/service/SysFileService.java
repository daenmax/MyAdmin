package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.common.oss.vo.UploadResult;
import cn.daenx.myadmin.system.po.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface SysFileService extends IService<SysFile> {


    /**
     * 上传文件
     *
     * @param file
     * @param remark 例如：用户头像、附件 等
     * @return
     */
    UploadResult uploadFile(MultipartFile file, String remark);
}
