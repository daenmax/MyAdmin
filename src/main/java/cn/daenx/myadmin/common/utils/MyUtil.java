package cn.daenx.myadmin.common.utils;

import cn.daenx.myadmin.common.exception.MyException;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
public class MyUtil {
    /**
     * 导出XLSX
     *
     * @param response
     * @param fileName    导出的文件名，不需要加.xlsx
     * @param sheetName   工作表名
     * @param list<T>
     * @param entityClass
     */
    public static <T> void exportXlsx(HttpServletResponse response, String fileName, String sheetName, List<T> list, Class entityClass) {
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        try {
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
//        } catch (UnsupportedEncodingException e) {
//            throw new MyException("导出XLS时发生错误");
//        }
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            throw new MyException("导出XLS时发生错误");
        }
        ExcelWriterBuilder write = EasyExcel.write(outputStream, entityClass);
        ExcelWriterSheetBuilder sheet = write.sheet(sheetName);
        sheet.doWrite(list);
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
}
