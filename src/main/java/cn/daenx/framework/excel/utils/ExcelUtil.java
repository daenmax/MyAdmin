package cn.daenx.framework.excel.utils;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.excel.DefaultExcelListener;
import cn.daenx.framework.excel.ExcelListener;
import cn.daenx.framework.excel.ExcelResult;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * EasyExcel导入导出工具类
 *
 * @author DaenMax
 */
@Slf4j
public class ExcelUtil {
    /**
     * 同步导入(适用于小数据量)
     *
     * @param is 输入流
     * @return 转换后集合
     */
    public static <T> List<T> importExcel(InputStream is, Class<T> clazz) {
        return EasyExcel.read(is).head(clazz).autoCloseStream(false).sheet().doReadSync();
    }


    /**
     * 使用校验监听器 异步导入 同步返回
     *
     * @param is         输入流
     * @param clazz      对象类型
     * @param isValidate 是否 Validator 检验 默认为是
     * @return 转换后集合
     */
    public static <T> ExcelResult<T> importExcel(InputStream is, Class<T> clazz, boolean isValidate) {
        DefaultExcelListener<T> listener = new DefaultExcelListener<>(isValidate);
        EasyExcel.read(is, clazz, listener).sheet().doRead();
        return listener.getExcelResult();
    }

    /**
     * 使用自定义监听器 异步导入 自定义返回
     *
     * @param is       输入流
     * @param clazz    对象类型
     * @param listener 自定义监听器
     * @return 转换后集合
     */
    public static <T> ExcelResult<T> importExcel(InputStream is, Class<T> clazz, ExcelListener<T> listener) {
        EasyExcel.read(is, clazz, listener).sheet().doRead();
        return listener.getExcelResult();
    }

    /**
     * 导出XLSX（只有一个sheet）
     *
     * @param response
     * @param fileName    导出的文件名，不需要加.xlsx
     * @param sheetName   工作表名
     * @param list<T>
     * @param entityClass
     */
    public static <T> void exportXlsx(HttpServletResponse response, String fileName, String sheetName, List<T> list, Class entityClass) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            throw new MyException("导出XLS时发生错误");
        }
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
     * 创建xlsx导出开始（适用于多个sheet）
     * 使用案例：
     * ExcelWriter writer = ExcelUtil.createExport(response, "测试文件");
     * ExcelUtil.writeSheet(writer, "合同列表", list, ContractPageDto.class);
     * ExcelUtil.writeSheet(writer, "流水列表", listBills, BusinessBillsPageDto.class);
     * ExcelUtil.finishWrite(writer);
     *
     * @param response
     * @param fileName 导出的文件名，不需要加.xlsx
     */
    public static <T> ExcelWriter createExport(HttpServletResponse response, String fileName) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            throw new MyException("导出XLS时发送错误");
        }
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            throw new MyException("导出XLS发送错误");
        }
        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
        return excelWriter;
    }

    /**
     * 写入一个sheet
     *
     * @param sheetName   工作表名
     * @param list<T>
     * @param entityClass
     */
    public static <T> void writeSheet(ExcelWriter excelWriter, String sheetName, List<T> list, Class entityClass) {
        WriteSheet sheet = EasyExcel.writerSheet(sheetName).head(entityClass).build();
        excelWriter.write(list, sheet);
    }

    /**
     * 结束写入sheet并导出
     */
    public static <T> void finishWrite(ExcelWriter excelWriter) {
        excelWriter.finish();
    }

}
