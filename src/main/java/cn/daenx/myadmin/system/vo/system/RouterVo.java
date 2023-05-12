package cn.daenx.myadmin.system.vo.system;

import cn.hutool.core.lang.Validator;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单表
 */
@Data
public class RouterVo implements Serializable {

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 显示状态，0=正常，1=隐藏
     */
    private boolean hidden;

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    private Boolean alwaysShow;

    /**
     * 其他元素
     */
    private MetaVo meta;

    /**
     * 子路由
     */
    private List<RouterVo> children;

    /**
     * 路由显示信息
     *
     */

    @Data
    public static class MetaVo {

        /**
         * 设置该路由在侧边栏和面包屑中展示的名字
         */
        private String title;

        /**
         * 设置该路由的图标，对应路径src/assets/icons/svg
         */
        private String icon;

        /**
         * 设置为true，则不会被 <keep-alive>缓存
         */
        private boolean noCache;

        /**
         * 内链地址（http(s)://开头）
         */
        private String link;

        public MetaVo(String title, String icon) {
            this.title = title;
            this.icon = icon;
        }

        public MetaVo(String title, String icon, boolean noCache) {
            this.title = title;
            this.icon = icon;
            this.noCache = noCache;
        }

        public MetaVo(String title, String icon, String link) {
            this.title = title;
            this.icon = icon;
            this.link = link;
        }

        public MetaVo(String title, String icon, boolean noCache, String link) {
            this.title = title;
            this.icon = icon;
            this.noCache = noCache;
            if (Validator.isUrl(link)) {
                this.link = link;
            }
        }

    }
}
