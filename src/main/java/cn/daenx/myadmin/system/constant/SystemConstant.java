package cn.daenx.myadmin.system.constant;

public class SystemConstant {
    /**
     * 用户状态：正常
     */
    public static final Integer USER_STATUS_NORMAL = 0;
    /**
     * 用户状态：停用
     */
    public static final Integer USER_STATUS_DISABLE = 1;
    /**
     * 用户状态：注销
     */
    public static final Integer USER_STATUS_OFF = 2;
    public static final String IS_ADMIN_ID = "1";


    /**
     * 登录结果：成功
     */
    public static final Integer LOGIN_SUCCESS = 0;
    /**
     * 登录结果：失败
     */
    public static final Integer LOGIN_FAIL = 1;

    /**
     * 菜单类型（目录）
     */
    public static final Integer MENU_TYPE_DIR = 1;
    /**
     * 菜单类型（菜单）
     */
    public static final Integer MENU_TYPE_MENU = 2;
    /**
     * 菜单类型（按钮）
     */
    public static final Integer MENU_TYPE_BUTTON = 3;

    /**
     * 是否菜单外链（是）
     */
    public static final Integer  YES_FRAME = 0;

    /**
     * 是否菜单外链（否）
     */
    public static final Integer  NO_FRAME = 1;

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


}
