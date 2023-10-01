package cn.daenx.framework.common.utils;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.system.config.SysUploadConfigVo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class MyUtil {

    /**
     * List对象转换
     *
     * @param collection
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> getListOfType(Collection<?> collection, Class<T> clazz) {
        List<T> outputList = new ArrayList<>();
        if (CollUtil.isEmpty(collection)) {
            return outputList;
        }
        for (Object obj : collection) {
            T targetObj = null;
            try {
                targetObj = clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                return outputList;
            }
            BeanUtils.copyProperties(obj, targetObj);
            outputList.add(targetObj);
        }
        return outputList;
    }

    public static String repeat(String str, int repeatCount) {
        String repeatedStr = "";
        for (int i = 0; i < repeatCount; i++) {
            repeatedStr += str;
        }
        return repeatedStr;
    }

    /**
     * 数据脱敏（中国）
     *
     * @param type  数据类型，0=姓名，1=手机号，2=身份证号码，3=银行卡号，4=电子邮箱，5=地址信息，6=IP地址
     * @param value 待脱敏的数据值
     * @return
     */
    public static String masked(Integer type, String value) {
        if (ObjectUtil.isEmpty(value)) {
            return null;
        }
        String maskedValue = value;
        switch (type) {
            case 0: // 姓名
                maskedValue = value.replaceAll("([\\u4e00-\\u9fa5])(.*)", "$1" + repeat("*", value.length() - 1));
                break;
            case 1: // 手机号
                maskedValue = value.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                break;
            case 2: // 身份证号码
                maskedValue = value.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1**********$2");
                break;
            case 3: // 银行卡号
                maskedValue = value.replaceAll("(\\d{4})\\d+(\\d{4})", "$1 **** **** $2");
                break;
            case 4: // 电子邮箱
                maskedValue = value.replaceAll("(\\w{1})\\w*@(.*)", "$1****@$2");
                break;
            case 5: // 地址信息
                maskedValue = value.replaceAll("([\\u4e00-\\u9fa5])(.*)", "$1" + repeat("*", value.length() - 1));
                break;
            case 6: // IP地址
                maskedValue = value.replaceAll("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})", "$1.$2.*.*");
                break;
            default:
                break;
        }
        return maskedValue;
    }

    /**
     * SM4加密
     *
     * @param data 明文
     * @param key  任意长度，任意字符串，因为取的是MD5
     * @return
     */
    public static String sm4Encrypt(String data, String key) {
        try {
            SymmetricCrypto sm4 = new SymmetricCrypto("SM4", Hex.decode(MD5.create().digestHex(key)));
            String encryptHex = sm4.encryptHex(data);
            return encryptHex;
        } catch (Exception e) {
            return data;
        }
    }

    /**
     * SM4解密
     *
     * @param data 密文
     * @param key  任意长度，任意字符串，因为取的是MD5
     * @return
     */
    public static String sm4Decrypt(String data, String key) {
        try {
            SymmetricCrypto sm4 = new SymmetricCrypto("SM4", Hex.decode(MD5.create().digestHex(key)));
            String decryptStr = sm4.decryptStr(data, CharsetUtil.CHARSET_UTF_8);
            return decryptStr;
        } catch (Exception e) {
            return data;
        }
    }

    /**
     * 检查腾讯验证码是否有效
     *
     * @param randStr
     * @param ticket
     * @return
     */
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
        String url = "https://accounts.qq.com/login/limit/proxy/domain/qq110.qq.com/v3/chkcaptcha?bkn=";
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

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

    /***
     * 获取本机主机名
     *
     * @return
     */
    public static String getHostName() {
        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "";
        }
        return hostName;
    }

    /***
     * 获取本机ip
     *
     * @return
     */
    public static String getHostIp() {
        String hostAddress;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
        return hostAddress;
    }


    /**
     * 检查后缀
     *
     * @param suffix            后缀，例如：.jpg
     * @param sysUploadConfigVo
     * @return
     */
    public static Boolean checkSuffix(String suffix, SysUploadConfigVo sysUploadConfigVo) {
        for (String s : sysUploadConfigVo.getFileType()) {
            if (suffix.equals("." + s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取IP地址
     *
     * @param ip
     * @return
     */
    public static String getIpLocation(String ip) {
        if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
            return "内网IP";
        }
        String res = HttpUtil.get("https://ip.useragentinfo.com/json?ip=" + ip);
        if (ObjectUtil.isNotEmpty(res)) {
            JSONObject jsonObject1 = JSONObject.parseObject(res);
            if (jsonObject1.getInteger("code") == 200) {
                return jsonObject1.getString("province") + jsonObject1.getString("city") + " " + jsonObject1.getString("isp");
            }
        }
        res = HttpUtil.get("http://opendata.baidu.com/api.php?query=" + ip + "&co=&resource_id=6006&oe=utf8");
        if (ObjectUtil.isNotEmpty(res)) {
            JSONObject jsonObject = JSONObject.parseObject(res);
            if ("0".equals(jsonObject.getString("status"))) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.size() > 0) {
                    JSONObject jsonObject1 = data.getJSONObject(0);
                    return jsonObject1.getString("location");
                }
            }
        }
        return "XXX XXX XXX";
    }

    /**
     * 拼接List<对象>中指定字段
     *
     * @param collection
     * @param function
     * @param flag       拼接符号
     * @param <E>
     * @return
     */
    public static <E> String join(Collection<E> collection, Function<E, String> function, String flag) {
        if (CollUtil.isEmpty(collection)) {
            return "";
        }
        return collection.stream().map(function).filter(Objects::nonNull).collect(Collectors.joining(flag));
    }

    /**
     * 拼接List<对象>中指定字段
     *
     * @param collection
     * @param function
     * @param <E>
     * @return
     */
    public static <E> List<String> joinToList(Collection<E> collection, Function<E, String> function) {
        if (CollUtil.isEmpty(collection)) {
            return new ArrayList<>();
        }
        return collection.stream().map(function).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 获取上传文件的md5
     * 32位小写
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public static String getMd5(MultipartFile multipartFile) {
        InputStream inputStream;
        try {
            inputStream = new ByteArrayInputStream(multipartFile.getBytes());
        } catch (IOException e) {
            throw new MyException("读取文件信息失败");
        }
        return SecureUtil.md5(inputStream);
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @param type 单位，1=KB，2=MB
     * @return
     */
    public static BigDecimal getFileSize(MultipartFile file, int type) {
        long size = file.getSize();
        BigDecimal sizeDecimal = new BigDecimal(size);
        BigDecimal kbDecimal = new BigDecimal(type == 1 ? "1024" : "1048576");
        BigDecimal sizeKB = sizeDecimal.divide(kbDecimal, 2, RoundingMode.HALF_UP);
        return sizeKB;
    }

    /**
     * 设置下载响应头
     *
     * @param response
     * @param originalName 文件名
     */
    public static void setDownloadResponseHeaders(HttpServletResponse response, String originalName) {
        String fileName = null;
        try {
            //对中文文件名进行编码
            fileName = URLEncoder.encode(originalName, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        fileName = fileName.replaceAll("\\+", "%20");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\";filename*=utf-8''" + fileName);
        response.setHeader("download-filename", fileName);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + "; charset=UTF-8");
    }

    /**
     * 获取指定格式的时间
     *
     * @param dateFormat 例如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateStrByFormat(String dateFormat) {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateFormat));
        return format;
    }

    /**
     * 字符串时间转LocalDateTime
     *
     * @param str        例如："2023-05-19 14:19:20"
     * @param dateFormat 例如"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static LocalDateTime strToLocalDateTime(String str, String dateFormat) {
        LocalDateTime localDateTime = LocalDateTime.parse(str, DateTimeFormatter.ofPattern(dateFormat));
        return localDateTime;
    }

    /**
     * 计算某个时间距离今天结束还剩多少秒
     *
     * @param currentDate
     * @return
     */
    public static Integer getRemainSecondsOneDay(LocalDateTime currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(ZoneOffset.of("+8")), ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(ZoneOffset.of("+8")), ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

    /**
     * 取两个时间相差的秒数的绝对值
     *
     * @param time1
     * @param time2
     * @return
     */
    public static Integer getDiffSec(LocalDateTime time1, LocalDateTime time2) {
        long l = Duration.between(time1, time2).toMillis() / 1000;
        if (l < 0) {
            l = l * -1;
        }
        return (int) l;
    }

    /**
     * 计算时间差
     *
     * @param nowDate 当前时间戳（13位）
     * @param date    目标时间（13位）
     * @return
     */
    public static String timeDistance(long nowDate, long date) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = nowDate - date;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        String str = "";
        if (day != 0) {
            str = str + day + "天";
        }
        if (hour != 0) {
            str = str + hour + "小时";
        }
        if (min != 0) {
            str = str + min + "分";
        }
        if (sec != 0) {
            str = str + sec + "秒";
        }
        return str;
    }

    /**
     * 计算时间差
     *
     * @param miss 毫秒
     * @return
     */
    public static String timeDistance(long miss) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = miss;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        String str = "";
        if (day != 0) {
            str = str + day + "天";
        }
        if (hour != 0) {
            str = str + hour + "小时";
        }
        if (min != 0) {
            str = str + min + "分";
        }
        if (sec != 0) {
            str = str + sec + "秒";
        }
        return str;
    }

    /**
     * 13位时间戳转LocalDateTime
     *
     * @param timestamp 例如：System.currentTimeMillis()
     * @return
     */
    public static LocalDateTime toLocalDateTime(long timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.of("+8"));
        return localDateTime;
    }

    /**
     * 获取当前10位时间戳
     *
     * @return
     */
    public static long getTimestamp10() {
        Instant now = Instant.now();
        long timestamp = now.getEpochSecond();
        return timestamp;
    }

    /**
     * 读取邮件HTLM模板，如果不存在就使用默认的
     *
     * @param systemName
     * @param code
     * @return
     */
    public static String buildCodeValida(String systemName, String code) {
        String html = "";
        try {
            //加载邮件html模板
            html = ResourceUtil.readUtf8Str("emailTemplate/codeValida.html");
        } catch (Exception e) {
            html = "您好！感谢您使用【systemInfoName】，您的验证码为：【code】，有效期30分钟，请尽快填写验证码完成验证！\n\nHello! Thanks for using 【systemInfoName】, the verification code is:【code】, valid for 30 minutes. Please fill in the verification code as soon as possible!";
        }
        html = html.replaceAll("【systemInfoName】", systemName).replaceAll("【code】", code);
        return html;
    }

    /**
     * 判断输入的账号类型
     * 1=账号，2=邮箱，3=手机号
     *
     * @param username
     * @return
     */
    public static Integer getUsernameType(String username) {
        if (username.contains("@")) {
            return 2;
        }
        if (NumberUtil.isNumber(username) && username.length() == 11) {
            return 3;
        }
        return 1;
    }

    /**
     * 转换到秒
     *
     * @param time 时间，例如：5
     * @param unit 单位，0=秒，1=分钟，2=小时，3=天
     * @return
     */
    public static Integer toSecond(Integer time, String unit) {
        Integer timeI = time;
        if (timeI == 0) {
            return 0;
        }
        if (CommonConstant.SYS_TIME_UNIT_SECOND.equals(unit)) {
            return timeI;
        } else if (CommonConstant.SYS_TIME_UNIT_MINUTE.equals(unit)) {
            timeI = timeI * 60;
            return timeI;
        } else if (CommonConstant.SYS_TIME_UNIT_HOUR.equals(unit)) {
            timeI = timeI * 60 * 60;
            return timeI;
        } else if (CommonConstant.SYS_TIME_UNIT_DAY.equals(unit)) {
            timeI = timeI * 60 * 60 * 24;
            return timeI;
        }
        return 0;
    }

    /**
     * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
     *
     * @param str  指定字符串
     * @param strs 需要检查的字符串数组
     * @return 是否匹配
     */
    public static boolean matches(String str, List<String> strs) {
        if (ObjectUtil.isEmpty(str) || CollUtil.isEmpty(strs)) {
            return false;
        }
        for (String pattern : strs) {
            if (isMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符;
     * * 表示一层路径内的任意字符串，不可跨层级;
     * ** 表示任意层路径;
     *
     * @param pattern 匹配规则
     * @param url     需要匹配的url
     */
    public static boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }
}
