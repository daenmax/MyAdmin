package cn.daenx.myadmin.test;

import cn.daenx.myadmin.common.constant.Constant;
import cn.daenx.myadmin.system.po.SysUser;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;

import java.time.LocalDateTime;

public class testaaaaa {
    public static void main(String[] args) {
        String format = LocalDateTimeUtil.format(LocalDateTime.now(), Constant.DATE_TIME_FORMAT);
        System.out.println("解除时间：" + format);
    }
}
