package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.oss.core.OssClient;
import cn.daenx.myadmin.common.oss.utils.OssUtil;
import cn.daenx.myadmin.common.oss.vo.UploadResult;
import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysFile;
import cn.daenx.myadmin.system.mapper.SysFileMapper;
import cn.daenx.myadmin.system.service.SysFileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {
    @Resource
    private SysFileMapper sysFileMapper;


    /**
     * 上传文件
     *
     * @param file
     * @param remark 例如：用户头像、附件 等
     * @return
     */
    @Override
    public UploadResult uploadFile(MultipartFile file, String remark) {
        OssClient ossClient = OssUtil.getOssClient();
        //通过MD5去判断是否已经上传过，避免重复上传占用资源
        LambdaQueryWrapper<SysFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFile::getFileMd5, MyUtil.getMd5(file));
        SysFile sysFileDb = sysFileMapper.selectOne(wrapper);
        if (sysFileDb != null) {
            //该文件已经上传过了，但是需要判断以下是否存储在当前的oss上，如果不是那么需要重新上传
            if (sysFileDb.getOssId().equals(ossClient.getOssProperties().getId())) {
                //当前OSS上已经上传过，直接返回，不再重复上传
                return UploadResult.builder()
                        .url(sysFileDb.getFileUrl())
                        .fileName(sysFileDb.getFileName())
                        .md5(sysFileDb.getFileMd5())
                        .suffix(sysFileDb.getFileSuffix())
                        .size(sysFileDb.getFileSize())
                        .contentType(sysFileDb.getFileType())
                        .sysFileId(sysFileDb.getId())
                        .build();
            }
        }
        //文件名，例如：大恩的头像.jpg
        String originalName = file.getOriginalFilename();
        //后缀，例如：.jpg
        String suffix = StringUtils.substring(originalName, originalName.lastIndexOf("."), originalName.length());
        //文件类型，例如：image/jpeg
        String contentType = file.getContentType();
        UploadResult upload;
        try {
            upload = ossClient.uploadSuffix(file.getBytes(), suffix, contentType);
        } catch (IOException e) {
            throw new MyException("上传文件失败[" + e.getMessage() + "]");
        }
        SysFile sysFile = new SysFile();
        sysFile.setOriginalName(originalName);
        sysFile.setFileName(upload.getFileName());
        sysFile.setFileSuffix(upload.getSuffix());
        sysFile.setFileUrl(upload.getUrl());
        sysFile.setFileSize(upload.getSize());
        sysFile.setFileMd5(upload.getMd5());
        sysFile.setFileType(upload.getContentType());
        sysFile.setOssId(ossClient.getOssProperties().getId());
        sysFile.setStatus(SystemConstant.STATUS_NORMAL);
        sysFile.setRemark(remark);
        sysFileMapper.insert(sysFile);
        upload.setSysFileId(sysFile.getId());
        return upload;
    }
}
