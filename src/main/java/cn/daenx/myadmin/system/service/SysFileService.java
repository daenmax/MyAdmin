package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.common.oss.vo.UploadResult;
import cn.daenx.myadmin.system.dto.SysFilePageDto;
import cn.daenx.myadmin.system.po.SysFile;
import cn.daenx.myadmin.system.vo.SysFilePageVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SysFileService extends IService<SysFile> {


    /**
     * 上传文件
     *
     * @param file
     * @param remark 例如：用户头像、附件 等
     * @return
     */
    UploadResult uploadFile(MultipartFile file, String remark);

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysFilePageDto> getPage(SysFilePageVo vo);

    /**
     * 删除
     * 同时也会删除所属OSS上的文件
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);
}
