package cn.daenx.system.service.impl;

import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.common.utils.RedisUtil;
import cn.daenx.framework.common.vo.system.config.SysCaptchaConfigVo;
import cn.daenx.system.domain.vo.SysSubmitCaptchaVo;
import cn.daenx.system.service.CaptchaService;
import cn.daenx.system.service.SysConfigService;
import cn.hutool.captcha.*;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
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
            map.put("captchaLock", false);
            return map;
        }
        map.put("captchaLock", Boolean.valueOf(sysCaptchaConfigVo.getConfig().getLock()));
        if ("false".equals(sysCaptchaConfigVo.getConfig().getLock())) {
            return map;
        }
        map.put("captchaType", sysCaptchaConfigVo.getConfig().getType());
        //0=图片验证码，1=腾讯验证码
        if (sysCaptchaConfigVo.getConfig().getType() == 0) {
            //0=图片验证码
            HashMap<String, Object> captchaImgToBase64 = createCaptchaImgToBase64(sysCaptchaConfigVo);
            map.put("image", captchaImgToBase64);
            return map;
        } else if (sysCaptchaConfigVo.getConfig().getType() == 1) {
            //1=腾讯验证码
            HashMap<String, Object> captchaSlider = createCaptchaSlider(sysCaptchaConfigVo);
            map.put("slider", captchaSlider);
            return map;
        }
        map.put("captchaLock", false);
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

    /**
     * 创建腾讯验证码
     *
     * @param sysCaptchaConfigVo
     * @return
     */
    @Override
    public HashMap<String, Object> createCaptchaSlider(SysCaptchaConfigVo sysCaptchaConfigVo) {
        HashMap<String, Object> map = new HashMap<>();
        String uuid = IdUtil.randomUUID();
        RedisUtil.setValue(RedisConstant.CAPTCHA_SLIDER + uuid, "1", 300L, TimeUnit.SECONDS);
        map.put("uuid", uuid);
        return map;
    }

    /**
     * 校验验证码
     *
     * @param vo
     */
    @Override
    public void validatedCaptcha(SysSubmitCaptchaVo vo) {
        SysCaptchaConfigVo sysCaptchaConfigVo = sysConfigService.getSysCaptchaConfigVo();
        if (!"true".equals(sysCaptchaConfigVo.getConfig().getLock())) {
            return;
        }
        if (sysCaptchaConfigVo.getConfig().getType() == 0) {
            //图片验证码
            if (ObjectUtil.isEmpty(vo.getCode()) || ObjectUtil.isEmpty(vo.getUuid())) {
                throw new MyException("验证码相关参数不能为空");
            }
            String codeReal = (String) RedisUtil.getValue(RedisConstant.CAPTCHA_IMG + vo.getUuid());
            if (ObjectUtil.isEmpty(codeReal)) {
                throw new MyException("验证码已过期，请刷新验证码");
            }
            if (!codeReal.equals(vo.getCode())) {
                RedisUtil.del(RedisConstant.CAPTCHA_IMG + vo.getUuid());
                throw new MyException("验证码错误");
            }
            RedisUtil.del(RedisConstant.CAPTCHA_IMG + vo.getUuid());
        } else if (sysCaptchaConfigVo.getConfig().getType() == 1) {
            //腾讯验证码
            if (ObjectUtil.isEmpty(vo.getRandStr()) || ObjectUtil.isEmpty(vo.getTicket()) || ObjectUtil.isEmpty(vo.getUuid())) {
                throw new MyException("验证码相关参数不能为空");
            }
            String codeReal = (String) RedisUtil.getValue(RedisConstant.CAPTCHA_SLIDER + vo.getUuid());
            if (ObjectUtil.isEmpty(codeReal)) {
                throw new MyException("验证参数已过期，请重新验证");
            }
            String md5 = SecureUtil.md5(vo.getRandStr() + vo.getTicket());
            if (!MyUtil.checkTencentCaptchaSlider(vo.getRandStr(), vo.getTicket())) {
                throw new MyException("验证失败，请重试");
            }
            RedisUtil.del(RedisConstant.CAPTCHA_SLIDER + vo.getUuid());
        }
    }
}
