package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.service.CaptchaService;
import cn.hutool.captcha.*;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Resource
    private RedisUtil redisUtil;

    /**
     * 验证码类型，1=线段干扰的验证码，2=圆圈干扰验证码，3=扭曲干扰验证码，4=GIF，5=加减运算
     */
    @Value("${captcha.img.type}")
    private int captchaImgType;
    /**
     * 宽度
     */
    @Value("${captcha.img.width}")
    private int captchaImgWidth;
    /**
     * 高度
     */
    @Value("${captcha.img.height}")
    private int captchaImgHeight;
    /**
     * 字符数
     */
    @Value("${captcha.img.codeCount}")
    private int captchaImgCodeCount;
    /**
     * 线段干扰元素数/线段干扰元素数/扭曲干扰线宽度
     */
    @Value("${captcha.img.olCount}")
    private int captchaImgOlCount;


    /**
     * 创建图片验证码
     *
     * @return
     */
    @Override
    public HashMap<String, Object> createCaptchaImgToBase64() {
        HashMap<String, Object> map = new HashMap<>();
        String base64 = "";
        String code = "";
        if (captchaImgType == 1) {
            //线段干扰的验证码
            LineCaptcha captcha = CaptchaUtil.createLineCaptcha(captchaImgWidth, captchaImgHeight, captchaImgCodeCount, captchaImgOlCount);
            captcha.createCode();
            code = captcha.getCode();
            base64 = captcha.getImageBase64();
        } else if (captchaImgType == 2) {
            //圆圈干扰验证码
            CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(captchaImgWidth, captchaImgHeight, captchaImgCodeCount, captchaImgOlCount);
            captcha.createCode();
            code = captcha.getCode();
            base64 = captcha.getImageBase64();
        } else if (captchaImgType == 3) {
            //扭曲干扰验证码
            ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(captchaImgWidth, captchaImgHeight, captchaImgCodeCount, captchaImgOlCount);
            captcha.createCode();
            code = captcha.getCode();
            base64 = captcha.getImageBase64();
        } else if (captchaImgType == 4) {
            //GIF
            GifCaptcha captcha = CaptchaUtil.createGifCaptcha(captchaImgWidth, captchaImgHeight, captchaImgCodeCount);
            captcha.createCode();
            code = captcha.getCode();
            base64 = captcha.getImageBase64();
        } else if (captchaImgType == 5) {
            //加减运算
            CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(captchaImgWidth, captchaImgHeight);
            int a = RandomUtil.randomInt(10, 20);
            int b = RandomUtil.randomInt(1, 10);
            int res;
            String type;
            if (RandomUtil.randomBoolean()) {
                res = a + b;
                type = "+";
            } else {
                res = a - b;
                type = "-";
            }
            code = String.valueOf(res);
            Image image = captcha.createImage(a + " " + type + " " + b + " =");
            base64 = ImgUtil.toBase64(image, ImgUtil.IMAGE_TYPE_PNG);
        }
        String uuid = IdUtil.randomUUID();
        redisUtil.set(RedisConstant.CAPTCHA_IMG + uuid, code, 300);
        map.put("uuid", uuid);
        map.put("img", base64);
        return map;
    }
}
