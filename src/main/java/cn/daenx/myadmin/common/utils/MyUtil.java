package cn.daenx.myadmin.common.utils;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.system.vo.system.SysUploadConfigVo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class MyUtil {
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
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = nowDate - date;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
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
            JSONObject jsonObject = JSONUtil.parseObj(res);
            if (jsonObject.getInt("code") == 200) {
                return jsonObject.getStr("province") + jsonObject.getStr("city") + " " + jsonObject.getStr("isp");
            }
        }
        res = HttpUtil.get("http://opendata.baidu.com/api.php?query=" + ip + "&co=&resource_id=6006&oe=utf8");
        if (ObjectUtil.isNotEmpty(res)) {
            JSONObject jsonObject = JSONUtil.parseObj(res);
            if ("0".equals(jsonObject.getStr("status"))) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.size() > 0) {
                    JSONObject jsonObject1 = data.get(0, JSONObject.class);
                    return jsonObject1.getStr("location");
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
        BigDecimal sizeKB = sizeDecimal.divide(kbDecimal, 2, BigDecimal.ROUND_HALF_UP);
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
    }
}
