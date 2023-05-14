package cn.daenx.myadmin.common.constant;

public class RedisConstant {

    public static final String CAPTCHA_IMG = "captcha:img:";
    public static final String CAPTCHA_PHONE = "captcha:phone:";
    public static final String CAPTCHA_EMAIL = "captcha:email:";

    public static final String DICT = "dict:";
    public static final String CONFIG = "config:";
    public static final String OSS_USE = "oss:use";
    public static final String OSS = "oss:";
    public static final String LOGIN_FAIL = "loginFail:";

    public static final String SEND_EMAIL = "send:email:";
    public static final String SEND_PHONE = "send:phone:";

    public static final String VALIDA_EMAIL = "valida:email:";
    public static final String VALIDA_PHONE = "valida:phone:";

    /***
     * 腾讯滑块验证码，因为check时，短时间内不会失效，所以本地记录一下是否已经check
     */
    public static final String CHECK_CAPTCHA_TENCENT = "tencent:captcha:";
}
