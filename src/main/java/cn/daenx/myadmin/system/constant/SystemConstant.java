package cn.daenx.myadmin.system.constant;

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
     * 通用状态：正常
     */
    public static final String STATUS_NORMAL = "0";
    /**
     * 通用状态：禁用
     */
    public static final String STATUS_DISABLE = "1";

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
     * 图片类型
     */
    public static final String[] IMAGE_SUFFIX = {".bmp", ".gif", ".jpg", ".jpeg", ".png"};

}
