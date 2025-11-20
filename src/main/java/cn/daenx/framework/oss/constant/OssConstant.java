package cn.daenx.framework.oss.constant;


/**
 * 对象存储常量
 *
 * @author Lion Li
 */
public interface OssConstant {


    /**
     * 云服务商
     * aliyun   阿里云
     * qcloud   腾讯云
     * qiniu    七牛云
     * jdcloud  京东云
     * bcebos  百度云
     * obs      华为云
     */
    String[] CLOUD_SERVICE = new String[]{"aliyun", "qcloud", "qiniu", "jdcloud", "bcebos", "obs"};

    /**
     * http请求
     */
    String HTTP = "http://";

    /**
     * https请求
     */
    String HTTPS = "https://";

}
