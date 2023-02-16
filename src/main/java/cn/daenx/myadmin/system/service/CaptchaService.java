package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

public interface CaptchaService {
    /**
     * 创建图片验证码
     *
     * @return
     */
    HashMap<String, Object> createCaptchaImgToBase64();
}
