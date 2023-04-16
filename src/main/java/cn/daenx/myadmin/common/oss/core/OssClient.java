package cn.daenx.myadmin.common.oss.core;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.oss.vo.UploadResult;
import cn.daenx.myadmin.common.oss.enums.AccessPolicyType;
import cn.daenx.myadmin.common.oss.enums.PolicyType;
import cn.daenx.myadmin.common.oss.vo.OssProperties;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * AWS S3接口协议，诸多云存储厂商都兼容此协议，例如：minio、阿里云OSS、腾讯云COS、七牛云、京东云、华为云
 */
public class OssClient {
    private final OssProperties properties;
    private final AmazonS3 client;
    /**
     * 云服务商
     * aliyun   阿里云
     * qcloud   腾讯云
     * qiniu    七牛云
     * jdcloud  京东云
     * obs      华为云
     */
    private String[] CLOUD_SERVICE = new String[]{"aliyun", "qcloud", "qiniu", "jdcloud", "obs"};

    /**
     * 初始化
     *
     * @param ossProperties
     */
    public OssClient(OssProperties ossProperties) {
        this.properties = ossProperties;
        try {
            AwsClientBuilder.EndpointConfiguration endpointConfig =
                    new AwsClientBuilder.EndpointConfiguration(properties.getEndpoint(), properties.getRegion());
            AWSCredentials credentials = new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());
            AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
            ClientConfiguration clientConfig = new ClientConfiguration();
            clientConfig.setProtocol("1".equals(properties.getIsHttps()) ? Protocol.HTTPS : Protocol.HTTP);
            AmazonS3ClientBuilder build = AmazonS3Client.builder()
                    .withEndpointConfiguration(endpointConfig)
                    .withClientConfiguration(clientConfig)
                    .withCredentials(credentialsProvider)
                    .disableChunkedEncoding();
            if (!StringUtils.containsAny(properties.getEndpoint(), CLOUD_SERVICE)) {
                // 使用云OSS时，需要关闭，如果是minio则开启即可
                build.enablePathStyleAccess();
            }
            this.client = build.build();
            createBucket();
        } catch (Exception e) {
            if (e instanceof MyException) {
                throw e;
            }
            throw new MyException("配置错误[" + e.getMessage() + "]");
        }
    }

    /**
     * 创建bucket
     */
    private void createBucket() {
        try {
            String bucketName = properties.getBucketName();
            if (client.doesBucketExistV2(bucketName)) {
                return;
            }
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            AccessPolicyType accessPolicy = getAccessPolicy();
            createBucketRequest.setCannedAcl(accessPolicy.getAcl());
            client.createBucket(createBucketRequest);
            client.setBucketPolicy(bucketName, getPolicy(bucketName, accessPolicy.getPolicyType()));
        } catch (Exception e) {
            throw new MyException("创建Bucket失败[" + e.getMessage() + "]");
        }
    }

    /**
     * 上传文件
     *
     * @param data        文件二进制
     * @param path        完整路径，例如：2023/04/16/27010e1b0bb6488c95619f5fc036dca3.jpg
     * @param contentType 文件类型，例如：image/jpeg
     * @return
     */
    public UploadResult upload(byte[] data, String path, String contentType) {
        return upload(new ByteArrayInputStream(data), path, contentType);
    }

    /**
     * 上传文件
     *
     * @param inputStream 文件流
     * @param path        完整路径，例如：2023/04/16/27010e1b0bb6488c95619f5fc036dca3.jpg
     * @param contentType 文件类型，例如：image/jpeg
     * @return
     */
    public UploadResult upload(InputStream inputStream, String path, String contentType) {
        if (!(inputStream instanceof ByteArrayInputStream)) {
            inputStream = new ByteArrayInputStream(IoUtil.readBytes(inputStream));
        }
        PutObjectResult putObjectResult;
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(inputStream.available());
            PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucketName(), path, inputStream, metadata);
            // 设置上传对象的 Acl 为公共读
            putObjectRequest.setCannedAcl(getAccessPolicy().getAcl());
            putObjectResult = client.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new MyException("上传文件失败[" + e.getMessage() + "]");
        }
        return UploadResult.builder().url(getUrl() + "/" + path).fileName(path).eTag(putObjectResult.getETag()).build();
    }

    /**
     * 上传文件
     *
     * @param data        文件二进制
     * @param suffix      后缀，例如：.jpg
     * @param contentType 文件类型，例如：image/jpeg
     * @return
     */
    public UploadResult uploadSuffix(byte[] data, String suffix, String contentType) {
        return upload(data, getPath(properties.getPrefix(), suffix), contentType);
    }

    /**
     * 上传文件
     *
     * @param inputStream 文件流
     * @param suffix      后缀，例如：.jpg
     * @param contentType 文件类型，例如：image/jpeg
     * @return
     */
    public UploadResult uploadSuffix(InputStream inputStream, String suffix, String contentType) {
        return upload(inputStream, getPath(properties.getPrefix(), suffix), contentType);
    }

    /**
     * 删除文件
     *
     * @param path 完整路径，例如：2023/04/16/27010e1b0bb6488c95619f5fc036dca3.jpg
     */
    public void delete(String path) {
        path = path.replace(getUrl() + "/", "");
        try {
            client.deleteObject(properties.getBucketName(), path);
        } catch (Exception e) {
            throw new MyException("删除文件失败[" + e.getMessage() + "]");
        }
    }

    /**
     * 获取文件元数据
     *
     * @param path 完整文件路径
     */
    public ObjectMetadata getObjectMetadata(String path) {
        path = path.replace(getUrl() + "/", "");
        S3Object object = client.getObject(properties.getBucketName(), path);
        return object.getObjectMetadata();
    }

    /**
     * 获取文件列表
     */
    public List<S3ObjectSummary> getObjectList() {
        ObjectListing objectListing = client.listObjects(properties.getBucketName(), properties.getPrefix());
        return new ArrayList<>(objectListing.getObjectSummaries());
    }

    /**
     * 获取文件流
     *
     * @param path
     * @return
     */
    public InputStream getObjectContent(String path) {
        path = path.replace(getUrl() + "/", "");
        S3Object object = client.getObject(properties.getBucketName(), path);
        return object.getObjectContent();
    }

    /**
     * 获取私有URL链接
     *
     * @param objectKey 对象KEY
     * @param second    授权时间
     */
    public String getPrivateUrl(String objectKey, Integer second) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(properties.getBucketName(), objectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(new Date(System.currentTimeMillis() + 1000L * second));
        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    /**
     * 检查配置是否相同
     */
    public boolean checkPropertiesSame(OssProperties properties) {
        return this.properties.equals(properties);
    }

    /**
     * 获取当前桶权限类型
     *
     * @return 当前桶权限类型code
     */
    private AccessPolicyType getAccessPolicy() {
        return AccessPolicyType.getByType(properties.getAccessPolicy());
    }

    private static String getPolicy(String bucketName, PolicyType policyType) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n\"Statement\": [\n{\n\"Action\": [\n");
        if (policyType == PolicyType.WRITE) {
            builder.append("\"s3:GetBucketLocation\",\n\"s3:ListBucketMultipartUploads\"\n");
        } else if (policyType == PolicyType.READ_WRITE) {
            builder.append("\"s3:GetBucketLocation\",\n\"s3:ListBucket\",\n\"s3:ListBucketMultipartUploads\"\n");
        } else {
            builder.append("\"s3:GetBucketLocation\"\n");
        }
        builder.append("],\n\"Effect\": \"Allow\",\n\"Principal\": \"*\",\n\"Resource\": \"arn:aws:s3:::");
        builder.append(bucketName);
        builder.append("\"\n},\n");
        if (policyType == PolicyType.READ) {
            builder.append("{\n\"Action\": [\n\"s3:ListBucket\"\n],\n\"Effect\": \"Deny\",\n\"Principal\": \"*\",\n\"Resource\": \"arn:aws:s3:::");
            builder.append(bucketName);
            builder.append("\"\n},\n");
        }
        builder.append("{\n\"Action\": ");
        switch (policyType) {
            case WRITE:
                builder.append("[\n\"s3:AbortMultipartUpload\",\n\"s3:DeleteObject\",\n\"s3:ListMultipartUploadParts\",\n\"s3:PutObject\"\n],\n");
                break;
            case READ_WRITE:
                builder.append("[\n\"s3:AbortMultipartUpload\",\n\"s3:DeleteObject\",\n\"s3:GetObject\",\n\"s3:ListMultipartUploadParts\",\n\"s3:PutObject\"\n],\n");
                break;
            default:
                builder.append("\"s3:GetObject\",\n");
                break;
        }
        builder.append("\"Effect\": \"Allow\",\n\"Principal\": \"*\",\n\"Resource\": \"arn:aws:s3:::");
        builder.append(bucketName);
        builder.append("/*\"\n}\n],\n\"Version\": \"2012-10-17\"\n}\n");
        return builder.toString();
    }

    /**
     * 获取前置地址
     *
     * @return
     */
    private String getUrl() {
        String domain = properties.getDomain();
        String endpoint = properties.getEndpoint();
        String header = "1".equals(properties.getIsHttps()) ? "https://" : "http://";
        // 云服务商直接返回
        if (StringUtils.containsAny(endpoint, CLOUD_SERVICE)) {
            if (StringUtils.isNotBlank(domain)) {
                return header + domain;
            }
            return header + properties.getBucketName() + "." + endpoint;
        }
        // minio 单独处理
        if (StringUtils.isNotBlank(domain)) {
            return header + domain + "/" + properties.getBucketName();
        }
        return header + endpoint + "/" + properties.getBucketName();
    }

    /**
     * 获取前置路径
     *
     * @param prefix
     * @param suffix
     * @return
     */
    private String getPath(String prefix, String suffix) {
        // 生成uuid
        String uuid = IdUtil.fastSimpleUUID();
        // 文件路径
        String path = DateFormatUtils.format(new Date(), "yyyy/MM/dd") + "/" + uuid;
        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        return path + suffix;
    }
}
