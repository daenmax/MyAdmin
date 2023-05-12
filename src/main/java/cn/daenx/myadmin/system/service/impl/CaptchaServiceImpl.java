package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.service.CaptchaService;
import cn.daenx.myadmin.system.service.SysConfigService;
import cn.daenx.myadmin.system.vo.system.SysCaptchaConfigVo;
import cn.hutool.captcha.*;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Resource
    private SysConfigService sysConfigService;

    /**
     * 创建验证码
     * 受系统参数配置影响
     *
     * @return
     */
    @Override
    public HashMap<String, Object> createCaptcha() {
        HashMap<String, Object> map = new HashMap<>();
        SysCaptchaConfigVo sysCaptchaConfigVo = sysConfigService.getSysCaptchaConfigVo();
        if (sysCaptchaConfigVo == null) {
            map.put("captchaLock", "false");
            return map;
        }
        map.put("captchaLock", sysCaptchaConfigVo.getConfig().getLock());
        if ("false".equals(sysCaptchaConfigVo.getConfig().getLock())) {
            return map;
        }
        map.put("captchaType", sysCaptchaConfigVo.getConfig().getType());
        //0=图片验证码，1=滑块验证码（待实现），2=文字点选（待实现）
        if (sysCaptchaConfigVo.getConfig().getType() == 0) {
            //0=图片验证码
            HashMap<String, Object> captchaImgToBase64 = createCaptchaImgToBase64(sysCaptchaConfigVo);
            map.put("image", captchaImgToBase64);
            return map;
        } else if (sysCaptchaConfigVo.getConfig().getType() == 1) {
            //TODO 1=滑块验证码（待实现）
            map.put("slider", null);
            return map;
        } else if (sysCaptchaConfigVo.getConfig().getType() == 2) {
            //TODO 文字点选（待实现）
            map.put("click", null);
            return map;
        }
        map.put("captchaLock", "false");
        return map;
    }

    /**
     * 创建图片验证码
     *
     * @param sysCaptchaConfigVo
     * @return
     */
    @Override
    public HashMap<String, Object> createCaptchaImgToBase64(SysCaptchaConfigVo sysCaptchaConfigVo) {
        HashMap<String, Object> map = new HashMap<>();
        String base64 = "";
        String code = "";
        Integer captchaImgType = sysCaptchaConfigVo.getImage().getType();
        if (captchaImgType == 1) {
            //线段干扰的验证码
            LineCaptcha captcha = CaptchaUtil.createLineCaptcha(sysCaptchaConfigVo.getImage().getWidth(), sysCaptchaConfigVo.getImage().getHeight(), sysCaptchaConfigVo.getImage().getCodeCount(), sysCaptchaConfigVo.getImage().getOlCount());
            captcha.createCode();
            code = captcha.getCode();
            base64 = captcha.getImageBase64();
        } else if (captchaImgType == 2) {
            //圆圈干扰验证码
            CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(sysCaptchaConfigVo.getImage().getWidth(), sysCaptchaConfigVo.getImage().getHeight(), sysCaptchaConfigVo.getImage().getCodeCount(), sysCaptchaConfigVo.getImage().getOlCount());
            captcha.createCode();
            code = captcha.getCode();
            base64 = captcha.getImageBase64();
        } else if (captchaImgType == 3) {
            //扭曲干扰验证码
            ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(sysCaptchaConfigVo.getImage().getWidth(), sysCaptchaConfigVo.getImage().getHeight(), sysCaptchaConfigVo.getImage().getCodeCount(), sysCaptchaConfigVo.getImage().getOlCount());
            captcha.createCode();
            code = captcha.getCode();
            base64 = captcha.getImageBase64();
        } else if (captchaImgType == 4) {
            //GIF
            GifCaptcha captcha = CaptchaUtil.createGifCaptcha(sysCaptchaConfigVo.getImage().getWidth(), sysCaptchaConfigVo.getImage().getHeight(), sysCaptchaConfigVo.getImage().getCodeCount());
            captcha.createCode();
            code = captcha.getCode();
            base64 = captcha.getImageBase64();
        } else if (captchaImgType == 5) {
            //加减运算
            CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(sysCaptchaConfigVo.getImage().getWidth(), sysCaptchaConfigVo.getImage().getHeight());
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
        RedisUtil.setValue(RedisConstant.CAPTCHA_IMG + uuid, code, 300L, TimeUnit.SECONDS);
        map.put("uuid", uuid);
        map.put("img", base64);
        return map;
    }
}
