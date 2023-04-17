package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.oss.core.OssClient;
import cn.daenx.myadmin.common.oss.enums.AccessPolicyType;
import cn.daenx.myadmin.common.oss.utils.OssUtil;
import cn.daenx.myadmin.common.oss.vo.OssProperties;
import cn.daenx.myadmin.common.oss.vo.UploadResult;
import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.dto.SysFilePageDto;
import cn.daenx.myadmin.system.po.SysOssConfig;
import cn.daenx.myadmin.system.vo.SysFilePageVo;
import cn.daenx.myadmin.test.dto.TestDataPageDto;
import cn.daenx.myadmin.test.po.TestData;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysFile;
import cn.daenx.myadmin.system.mapper.SysFileMapper;
import cn.daenx.myadmin.system.service.SysFileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {
    @Resource
    private SysFileMapper sysFileMapper;

    /**
     * 如果是私有存储，那么获取一个120秒有效的链接
     *
     * @param url
     * @param path
     * @param ossConfigId
     * @return
     */
    private String transPrivateUrl(String url, String path, String ossConfigId) {
        Object object = RedisUtil.getValue(RedisConstant.OSS + ossConfigId);
        if (ObjectUtil.isEmpty(object)) {
            throw new MyException("未找到OSS配置信息，请联系管理员");
        }
        OssProperties ossProperties = JSON.parseObject(JSON.toJSONString(object), OssProperties.class);
        if (ossProperties != null) {
            if (ossProperties.getAccessPolicy().equals(AccessPolicyType.PRIVATE.getType())) {
                OssClient ossClient = OssUtil.getOssClientByOssProperties(ossProperties);
                String privateUrl = ossClient.getPrivateUrl(path, 120);
                return privateUrl;
            }
        }
        return url;
    }

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
                        .originalName(sysFileDb.getOriginalName())
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
        sysFile.setFileUrl(transPrivateUrl(upload.getUrl(), upload.getFileName(), ossClient.getOssProperties().getId()));
        sysFile.setFileSize(upload.getSize());
        sysFile.setFileMd5(upload.getMd5());
        sysFile.setFileType(upload.getContentType());
        sysFile.setOssId(ossClient.getOssProperties().getId());
        sysFile.setStatus(SystemConstant.STATUS_NORMAL);
        sysFile.setRemark(remark);
        sysFileMapper.insert(sysFile);
        upload.setSysFileId(sysFile.getId());
        upload.setOriginalName(originalName);
        return upload;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<SysFilePageDto> getPage(SysFilePageVo vo) {
        QueryWrapper<SysFile> wrapper = new QueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getOriginalName()), "sf.original_name", vo.getOriginalName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getFileName()), "sf.file_name", vo.getFileName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getFileSuffix()), "sf.file_suffix", vo.getFileSuffix());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getFileUrl()), "sf.file_url", vo.getFileUrl());
        wrapper.between(ObjectUtil.isNotEmpty(vo.getFileSizeMin()) && ObjectUtil.isNotEmpty(vo.getFileSizeMax()), "sf.file_size", vo.getFileSizeMin(), vo.getFileSizeMax());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getFileMd5()), "sf.file_md5", vo.getFileMd5());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getFileType()), "sf.file_type", vo.getFileType());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getOssId()), "sf.oss_id", vo.getOssId());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getStatus()), "sf.status", vo.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), "sf.remark", vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), "sf.create_time", startTime, endTime);
        wrapper.eq("sf.is_delete", SystemConstant.IS_DELETE_NO);
        IPage<SysFilePageDto> iPage = sysFileMapper.getPageWrapper(vo.getPage(true), wrapper);
        for (SysFilePageDto record : iPage.getRecords()) {
            //如果是私有存储，那么获取一个120秒有效的链接
            record.setFileUrl(transPrivateUrl(record.getFileUrl(), record.getFileName(), record.getOssId()));
        }
        return iPage;
    }

    /**
     * 删除
     * 同时也会删除所属OSS上的文件
     *
     * @param ids
     */
    @Override
    @DataScope(alias = "sys_file")
    public void deleteByIds(List<String> ids) {
        List<SysFile> sysFiles = listByIds(ids);
        for (SysFile sysFile : sysFiles) {
            int i = sysFileMapper.deleteById(sysFile.getId());
            if (i > 0) {
                OssClient ossClient = OssUtil.getOssClientByOssConfigId(sysFile.getOssId());
                ossClient.delete(sysFile.getFileName());
            }
        }
    }

    /**
     * 查询文件列表，根据文件ID数组
     *
     * @param fileIds
     * @return
     */
    @Override
    public List<SysFile> getListByIds(List<String> fileIds) {
        List<SysFile> sysFiles = listByIds(fileIds);
        for (SysFile sysFile : sysFiles) {
            sysFile.setFileUrl(transPrivateUrl(sysFile.getFileUrl(), sysFile.getFileName(), sysFile.getOssId()));
        }
        return sysFiles;
    }
}
