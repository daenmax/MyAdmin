package cn.daenx.framework.excel.utils;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.excel.DefaultExcelListener;
import cn.daenx.framework.excel.ExcelListener;
import cn.daenx.framework.excel.ExcelResult;
import cn.daenx.framework.excel.ReadRetVo;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EasyExcel导入导出工具类
 *
 * @author DaenMax
 */
@Slf4j
public class ExcelUtil {

    /**
     * 单sheet导入：同步导入(适用于小数据量)
     *
     * @param is 输入流
     * @return 转换后集合
     */
    public static <T> List<T> importExcel(InputStream is, Class<T> clazz) {
        return EasyExcel.read(is).head(clazz).autoCloseStream(false).sheet().doReadSync();
    }


    /**
     * 单sheet导入：使用校验监听器 异步导入 同步返回
     * <p>
     * 使用案例：
     * ExcelResult<TestDataImportVo> excelResult = ExcelUtil.importExcel(file.getInputStream(), TestDataImportVo.class, true);
     * List<TestDataImportVo> dataList = excelResult.getList();
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
     * 单sheet导入：使用自定义监听器 异步导入 自定义返回
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
     * 多sheet导入：使用校验监听器 异步导入 同步返回
     * <p>
     * 使用案例：
     * ExcelReader reader = ExcelUtil.createImport(file.getInputStream());
     * ReadRetVo<TestSheetAVo> sheetA = ExcelUtil.readSheet("班级信息", TestSheetAVo.class, true);
     * ReadRetVo<TestSheetBVo> sheetB = ExcelUtil.readSheet("学生信息", TestSheetBVo.class, true);
     * ExcelUtil.finishRead(reader, sheetA, sheetB);
     * <p>
     * ExcelResult<TestSheetAVo> sheetAResult = ExcelUtil.transResult(sheetA);
     * ExcelResult<TestSheetBVo> sheetBResult = ExcelUtil.transResult(sheetB);
     * <p>
     * List<TestSheetAVo> sheetAList = sheetAResult.getList();
     * List<TestSheetBVo> sheetBList = sheetBResult.getList();
     */
    public static ExcelReader createImport(InputStream is) {
        return EasyExcel.read(is).build();
    }

    /**
     * 读取一个sheet
     */
    public static <T> ReadRetVo<T> readSheet(String sheetName, Class<T> clazz, boolean isValidate) {
        DefaultExcelListener<T> listener = new DefaultExcelListener<>(isValidate);
        ReadSheet sheet = EasyExcel.readSheet(sheetName).head(clazz).registerReadListener(listener).build();
        ReadRetVo retVo = new ReadRetVo();
        retVo.setSheet(sheet);
        retVo.setListener(listener);
        return retVo;
    }

    /**
     * 结束读取sheet
     *
     * @param reader     调用createImport()返回的
     * @param readRetVos 调用readSheet()返回的
     */
    public static void finishRead(ExcelReader reader, ReadRetVo... readRetVos) {
        List<ReadRetVo> retVos = Arrays.asList(readRetVos);
        List<ReadSheet> sheets = retVos.stream().map(ReadRetVo::getSheet).collect(Collectors.toList());
        reader.read(sheets);
        return;
    }

    /**
     * 转换数据并读取
     *
     * @param retVo 调用readSheet()返回的
     * @param <T>
     * @return
     */
    public static <T> ExcelResult<T> transResult(ReadRetVo<T> retVo) {
        DefaultExcelListener<T> listener = retVo.getListener();
        return listener.getExcelResult();
    }


    //*****导出*****


    /**
     * 单sheet导出：XLSX
     * <p>
     * 使用案例：
     * List<TestDataPageDto> list = ...;//此处省略数组如何来的
     * ExcelUtil.exportXlsx(response, "测试数据", "测试数据", list, TestDataPageDto.class);
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
     * 多sheet导出：创建导出开始
     * <p>
     * 使用案例：
     * ExcelWriter writer = ExcelUtil.createExport(response, "多sheet表测试");
     * List<TestSheetAVo> sheetAList = ...;//此处省略数组如何来的
     * ExcelUtil.writeSheet(writer, "班级信息", sheetAList, TestSheetAVo.class);
     * List<TestSheetBVo> sheetBList = ...;//此处省略数组如何来的
     * ExcelUtil.writeSheet(writer, "学生信息", sheetBList, TestSheetBVo.class);
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
