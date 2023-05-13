package cn.daenx.myadmin.test;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;

public class testRun {
    public static void main(String[] args) {
        String randStr = "@ehS";
        String ticket = "t03GcVksUjjVZEjqvuH5J9PR22qeZmHrYlDBBuqLT95miI68pSxXdcLZnDZ9Mo9aUHQotfxUGTcGiHVcMSJx_geug0HuKJ3-6OE-agNlER_UF1ZKjskv5dnkiuHrECa6vaR";
        System.out.println(checkTencentCaptchaSlider(randStr, ticket));
    }

    public static Boolean checkTencentCaptchaSlider(String randStr, String ticket) {
        JSONObject unlgn = new JSONObject();
        unlgn.put("uin", 10001);
        JSONObject com = new JSONObject();
        com.put("src", 1);
        com.put("scene", 100607);
        com.put("platform", 5);
        com.put("version", "0.0.0");
        com.put("unlgn", unlgn);
        JSONObject req = new JSONObject();
        req.put("com", com);
        req.put("ticket", ticket);
        req.put("randStr", randStr);
        req.put("appid", 2090581062);
        String url = "https://accounts.qq.com/login/limit/proxy/domain/cloud.tencent.com/v3/chkcaptcha?bkn=";
        String body = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .header("qname-service", "1935233:65536")
                .header("qname-space", "Production")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36")
                .body(req.toJSONString()).execute().body();
        //{"retcode":0,"retmsg":""}
        //{"retcode":1500,"retmsg":"验证码校验错误"}
        JSONObject ret = JSONObject.parseObject(body);
        if (ObjectUtil.isEmpty(ret)) {
            return false;
        }
        Integer retcode = ret.getInteger("retcode");
        return retcode == 0;
    }

}
