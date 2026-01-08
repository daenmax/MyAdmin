package cn.daenx.data.system.service.impl;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.vo.system.config.SysUploadConfigVo;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.oss.core.OssClient;
import cn.daenx.framework.oss.enums.AccessPolicyType;
import cn.daenx.framework.oss.utils.OssUtil;
import cn.daenx.framework.oss.vo.OssProperties;
import cn.daenx.framework.oss.vo.UploadResult;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.cache.utils.CacheUtil;
import cn.daenx.data.system.domain.dto.SysFilePageDto;
import cn.daenx.data.system.service.SysConfigService;
import cn.daenx.data.system.domain.vo.SysFilePageVo;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.data.system.domain.po.SysFile;
import cn.daenx.data.system.mapper.SysFileMapper;
import cn.daenx.data.system.service.SysFileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {
    @Resource
    private SysFileMapper sysFileMapper;
    @Resource
    private SysConfigService sysConfigService;

    /**
     * 如果是私有存储，那么获取一个临时有效的链接
     *
     * @param url
     * @param path
     * @param ossConfigId
     * @return
     */
    private String transPrivateUrl(String url, String path, String ossConfigId) {
        Object object = CacheUtil.getValue(RedisConstant.OSS + ossConfigId);
        if (ObjectUtil.isEmpty(object)) {
            throw new MyException("未找到OSS配置信息，请联系管理员");
        }
        OssProperties ossProperties = JSON.parseObject(JSON.toJSONString(object), OssProperties.class);
        if (ossProperties != null) {
            if (ossProperties.getAccessPolicy().equals(AccessPolicyType.PRIVATE.getType())) {
                String cacheUrl = (String) CacheUtil.getValue(RedisConstant.OSS_CACHE + path);
                if (StringUtils.isNotBlank(cacheUrl)) {
                    return cacheUrl;
                }
                OssClient ossClient = OssUtil.getOssClientByOssProperties(ossProperties);
                String privateUrl = ossClient.getPrivateUrl(path, Duration.ofSeconds(ossProperties.getUrlValidAccessTime()));
                CacheUtil.setValue(RedisConstant.OSS_CACHE + path, privateUrl, Long.valueOf(ossProperties.getUrlValidCacheTime()), TimeUnit.SECONDS);
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
    public UploadResult upload(MultipartFile file, String remark) {
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
                        .fileUrl(transPrivateUrl(sysFileDb.getFileUrl(), sysFileDb.getFileName(), ossClient.getOssProperties().getId()))
                        .fileName(sysFileDb.getFileName())
                        .fileMd5(sysFileDb.getFileMd5())
                        .fileSuffix(sysFileDb.getFileSuffix())
                        .fileSize(sysFileDb.getFileSize())
                        .fileType(sysFileDb.getFileType())
                        .sysFileId(sysFileDb.getId())
                        .originalName(sysFileDb.getOriginalName())
                        .isExist(true)
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
            throw new MyException("上传文件失败[" + OssUtil.transErrMsg(e.getMessage()) + "]");
        }
        SysFile sysFile = new SysFile();
        sysFile.setOriginalName(originalName);
        sysFile.setFileName(upload.getFileName());
        sysFile.setFileSuffix(upload.getFileSuffix());
        sysFile.setFileUrl(transPrivateUrl(upload.getFileUrl(), upload.getFileName(), ossClient.getOssProperties().getId()));
        upload.setFileUrl(sysFile.getFileUrl());
        sysFile.setFileSize(upload.getFileSize());
        sysFile.setFileMd5(upload.getFileMd5());
        sysFile.setFileType(upload.getFileType());
        sysFile.setOssId(ossClient.getOssProperties().getId());
        sysFile.setStatus(CommonConstant.STATUS_NORMAL);
        sysFile.setRemark(remark);
        sysFileMapper.insert(sysFile);
        upload.setSysFileId(sysFile.getId());
        upload.setOriginalName(originalName);
        upload.setIsExist(false);
        return upload;
    }

    /**
     * 上传文件的前置，有限制策略
     *
     * @param file
     * @param remark 例如：用户头像、附件 等
     * @return
     */
    @Override
    public UploadResult uploadFile(MultipartFile file, String remark) {
        //文件名，例如：大恩的头像.jpg
        String originalName = file.getOriginalFilename();
        //后缀，例如：.jpg
        String suffix = StringUtils.substring(originalName, originalName.lastIndexOf("."), originalName.length());
        if (ObjectUtil.isEmpty(suffix)) {
            throw new MyException("错误的文件类型");
        }
        SysUploadConfigVo sysUploadConfigVo = sysConfigService.getSysUploadFileSuffixs();
        if (sysUploadConfigVo == null) {
            throw new MyException("当前系统不允许上传文件");
        }
        BigDecimal fileSize = MyUtil.getFileSize(file, 2);
        if (fileSize.compareTo(new BigDecimal(sysUploadConfigVo.getFileSize())) > 0) {
            throw new MyException("最大支持上传[" + sysUploadConfigVo.getFileSize() + "]MB的文件");
        }
        if (!MyUtil.checkSuffix(suffix, sysUploadConfigVo)) {
            throw new MyException("文件类型[" + suffix + "]不允许上传");
        }
        return upload(file, remark);
    }

    /**
     * 上传图片的前置，有限制策略
     *
     * @param file
     * @param remark 例如：用户头像、附件 等
     * @return
     */
    @Override
    public UploadResult uploadImage(MultipartFile file, String remark) {
        //文件名，例如：大恩的头像.jpg
        String originalName = file.getOriginalFilename();
        //后缀，例如：.jpg
        String suffix = StringUtils.substring(originalName, originalName.lastIndexOf("."), originalName.length());
        if (ObjectUtil.isEmpty(suffix)) {
            throw new MyException("错误的文件类型");
        }
        SysUploadConfigVo sysUploadConfigVo = sysConfigService.getSysUploadImageSuffixs();
        if (sysUploadConfigVo == null) {
            throw new MyException("当前系统不允许上传图片");
        }
        BigDecimal fileSize = MyUtil.getFileSize(file, 2);
        if (fileSize.compareTo(new BigDecimal(sysUploadConfigVo.getFileSize())) > 0) {
            throw new MyException("最大支持上传[" + sysUploadConfigVo.getFileSize() + "MB]的图片");
        }
        if (!MyUtil.checkSuffix(suffix, sysUploadConfigVo)) {
            throw new MyException("文件类型[" + suffix + "]不允许上传");
        }
        return upload(file, remark);
    }

    private QueryWrapper<SysFile> getWrapper(SysFilePageVo vo) {
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
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<SysFilePageDto> getPage(SysFilePageVo vo) {
        QueryWrapper<SysFile> wrapper = getWrapper(vo);
        IPage<SysFilePageDto> iPage = sysFileMapper.getPageWrapper(vo.getPage(true), wrapper);
        for (SysFilePageDto record : iPage.getRecords()) {
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

    /**
     * 下载文件
     *
     * @param id       文件ID
     * @param response
     */
    @Override
    public void download(String id, HttpServletResponse response) {
        SysFile sysFile = sysFileMapper.selectById(id);
        if (sysFile == null) {
            throw new MyException("文件不存在");
        }
        if (!sysFile.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            throw new MyException("该文件状态不可用");
        }
        MyUtil.setDownloadResponseHeaders(response, sysFile.getOriginalName());
        OssClient ossClient = OssUtil.getOssClientByOssConfigId(sysFile.getOssId());
        try {
            ossClient.download(sysFile.getFileName(), response.getOutputStream(), response::setContentLengthLong);
        } catch (Exception e) {
            throw new MyException("下载文件失败[" + OssUtil.transErrMsg(e.getMessage()) + "]");
        }

    }
}
