package cn.daenx.framework.common.constant;


import cn.daenx.framework.common.vo.system.config.SysUploadConfigVo;

public class SystemConstant {


    /**
     * 是否删除：否
     */
    public static final Integer IS_DELETE_NO = 0;
    /**
     * 是否删除：是
     */
    public static final Integer IS_DELETE_YES = 1;

    /**
     * 用户状态：正常
     */
    public static final String USER_STATUS_NORMAL = "0";
    /**
     * 用户状态：停用
     */
    public static final String USER_STATUS_DISABLE = "1";
    /**
     * 用户状态：注销
     */
    public static final String USER_STATUS_OFF = "2";
    public static final String IS_ADMIN_ID = "1";


    /**
     * 登录结果：成功
     */
    public static final String LOGIN_SUCCESS = "0";
    /**
     * 登录结果：失败
     */
    public static final String LOGIN_FAIL = "1";

    /**
     * 菜单类型（目录）
     */
    public static final String MENU_TYPE_DIR = "1";
    /**
     * 菜单类型（菜单）
     */
    public static final String MENU_TYPE_MENU = "2";
    /**
     * 菜单类型（按钮）
     */
    public static final String MENU_TYPE_BUTTON = "3";

    /**
     * 是否菜单外链（是）
     */
    public static final String YES_FRAME = "0";

    /**
     * 是否菜单外链（否）
     */
    public static final String NO_FRAME = "1";

    /**
     * Layout组件标识
     */
    public static final String LAYOUT = "Layout";

    /**
     * ParentView组件标识
     */
    public static final String PARENT_VIEW = "ParentView";

    /**
     * InnerLink组件标识
     */
    public static final String INNER_LINK = "InnerLink";


    /**
     * 数据权限，0=本人数据
     */
    public static final String DATA_SCOPE_SELF = "0";

    /**
     * 数据权限，1=本部门数据
     */
    public static final String DATA_SCOPE_DEPT = "1";

    /**
     * 数据权限，2=本部门及以下数据
     */
    public static final String DATA_SCOPE_DEPT_DOWN = "2";

    /**
     * 数据权限，3=全部数据
     */
    public static final String DATA_SCOPE_ALL = "3";

    /**
     * 数据权限，4=自定义权限
     */
    public static final String DATA_SCOPE_CUSTOM = "4";

    /**
     * OSS正在使用：否
     */
    public static final String IN_USE_NO = "0";
    /**
     * OSS正在使用：是
     */
    public static final String IN_USE_YES = "1";

    /**
     * 文件来源
     */
    public static final String FILE_FROM_AVATAR = "用户头像";

    /**
     * 文件来源
     */
    public static final String FILE_FROM_USER = "用户上传";

    /**
     * 默认的文件上传限制策略
     */
    public static final SysUploadConfigVo UPLOAD_CONFIG_FILE = new SysUploadConfigVo(3, 5, new String[]{"zip", "txt"}, true);
    /**
     * 默认的图片上传限制策略
     */
    public static final SysUploadConfigVo UPLOAD_CONFIG_IMAGE = new SysUploadConfigVo(4, 2, new String[]{"bmp", "gif", "jpg", "jpeg", "png"}, true);


    /**
     * 邮箱使用模式_轮询
     */
    public static final String EMAIL_MODE_POLL = "0";

    /**
     * 邮箱使用模式_完全随机
     */
    public static final String EMAIL_MODE_RANDOM = "1";

    /**
     * 邮箱使用模式_权重随机
     */
    public static final String EMAIL_MODE_RANDOM_WEIGHT = "2";
    /**
     * 邮箱redis队列key
     */
    public static final String EMAIL_POLL_KEY = "emailPoll";

    /**
     * 通知渠道，不通知
     */
    public static final String NOTIFY_CHANNEL_NO = "0";
    /**
     * 通知渠道，邮件
     */
    public static final String NOTIFY_CHANNEL_EMAIL = "1";
    /**
     * 通知渠道，短信
     */
    public static final String NOTIFY_CHANNEL_SMS = "2";
    /**
     * 通知渠道，钉钉
     */
    public static final String NOTIFY_CHANNEL_DING = "3";
    /**
     * 通知渠道，飞书
     */
    public static final String NOTIFY_CHANNEL_FEISHU = "4";
    /**
     * 通知渠道，企业微信
     */
    public static final String NOTIFY_CHANNEL_WECOM = "5";


    /**
     * 接口限制类型，0=限流，1=停用
     */
    public static final String API_LIMIT_CURRENT = "0";
    /**
     * 接口限制类型，0=限流，1=停用
     */
    public static final String API_LIMIT_CLOSE = "1";


    /**
     * 接口限流redis lua脚本返回值： 超过单个用户访问限制
     */
    public static final Long API_LIMIT_SINGLE_LIMIT_EXCEED = -1L;

    /**
     * 接口限流redis lua脚本返回值： 超过全部用户访问限制
     */
    public static final Long API_LIMIT_WHOLE_LIMIT_EXCEED = -2L;
}
