package cn.daenx.myadmin.quartz.constant;

public class QuartzConstant {
    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS 远程方法调用
     */
    public static final String LOOKUP_LDAPS = "ldaps:";

    /**
     * 定时任务白名单配置（仅允许访问的包名，如其他需要可以自行添加）
     */
    public static final String[] JOB_WHITELIST_STR = { "cn.daenx" };

    /**
     * 定时任务违规的字符
     */
    public static final String[] JOB_ERROR_STR = { "java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache"};


    /**
     * 定时任务执行结果：成功
     */
    public static final String JOB_SUCCESS = "0";
    /**
     * 定时任务执行结果：失败
     */
    public static final String JOB_FAIL = "1";
}
