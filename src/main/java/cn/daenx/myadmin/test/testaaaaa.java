package cn.daenx.myadmin.test;

import cn.daenx.myadmin.common.constant.Constant;
import cn.daenx.myadmin.system.po.SysUser;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

public class testaaaaa {
    public static void main(String[] args) {
        String format1 = "";
        String format2 = null;
        System.out.println(format1==null);
        System.out.println(format2==null);
        System.out.println(StringUtils.isEmpty(format1));
        System.out.println(StringUtils.isEmpty(format2));
    }
}
