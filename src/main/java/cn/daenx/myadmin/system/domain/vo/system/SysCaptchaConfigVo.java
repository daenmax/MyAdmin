package cn.daenx.myadmin.system.domain.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统验证码配置
 */
@Data
@AllArgsConstructor
public class SysCaptchaConfigVo implements Serializable {

    private Config config;
    private Image image;
    private Slider slider;

    /**
     * 配置
     */
    @Data
    public static class Config {
        /**
         * 验证码类型
         * 0=图片验证码，1=滑块验证码
         */
        private Integer type;

        /**
         * 是否启用验证码
         * 登录、注册、修改密码
         * true/false
         */
        private String lock;
    }

    /**
     * 图片验证码
     */
    @Data
    public static class Image {
        /**
         * 图片验证码类型
         * 1=线段干扰的验证码，2=圆圈干扰验证码，3=扭曲干扰验证码，4=GIF，5=加减运算
         */
        private Integer type;
        /**
         * 宽度
         * 推荐：200
         */
        private Integer width;
        /**
         * 高度
         * 推荐：100
         */
        private Integer height;
        /**
         * 字符数
         * 推荐：4
         */
        private Integer codeCount;
        /**
         * 线段干扰元素数/线段干扰元素数/扭曲干扰线宽度
         * 推荐：10
         */
        private Integer olCount;
    }

    /**
     * 滑块验证码（目前不需要任何配置）
     */
    @Data
    public static class Slider {

    }


}
