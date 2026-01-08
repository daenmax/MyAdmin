package cn.daenx.modules.system.service;

import cn.daenx.modules.system.domain.vo.sysFile.SysFilePageVo;
import cn.daenx.framework.oss.vo.UploadResult;
import cn.daenx.modules.system.domain.po.SysFile;
import cn.daenx.modules.system.domain.dto.sysFile.SysFilePageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
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
    UploadResult upload(MultipartFile file, String remark);

    /**
     * 上传文件的前置，有限制策略
     *
     * @param file
     * @param remark 例如：用户头像、附件 等
     * @return
     */
    UploadResult uploadFile(MultipartFile file, String remark);

    /**
     * 上传图片的前置，有限制策略
     *
     * @param file
     * @param remark 例如：用户头像、附件 等
     * @return
     */
    UploadResult uploadImage(MultipartFile file, String remark);

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysFilePageVo> getPage(SysFilePageDto vo);

    /**
     * 删除
     * 同时也会删除所属OSS上的文件
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 查询文件列表，根据文件ID数组
     *
     * @param fileIds
     * @return
     */
    List<SysFile> getListByIds(List<String> fileIds);

    /**
     * 下载文件
     *
     * @param id       文件ID
     * @param response
     */
    void download(String id, HttpServletResponse response);
}
