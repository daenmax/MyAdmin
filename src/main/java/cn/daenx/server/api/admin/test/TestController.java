package cn.daenx.server.api.admin.test;

import cn.daenx.framework.common.constant.enums.LogOperType;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.excel.ExcelResult;
import cn.daenx.framework.excel.ReadRetVo;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.framework.logSave.annotation.Log;
import cn.daenx.framework.repeatSubmit.annotation.RepeatSubmit;
import cn.daenx.data.test.domain.vo.TestSheetAVo;
import cn.daenx.data.test.domain.vo.TestSheetBVo;
import cn.dev33.satoken.annotation.SaIgnore;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Tag(name = "功能测试", description = "对系统内的一些功能进行测试和展示使用方法")
@RestController
@SaIgnore
@RequestMapping("/test")
@Slf4j
public class TestController {

    /**
     * 测试接口
     *
     * @return
     */
    @Operation(method = "肯定是GET啦", summary = "测试接口", description = "我是description")
    @GetMapping("/test")
    public Result test() {
        return Result.ok("查询成功");
    }


    /**
     * 测试接口
     *
     * @return
     */
    @Operation(method = "肯定是GET啦", summary = "测试接口", description = "我是description")
    @GetMapping("/test2")
    @RepeatSubmit
    public Result test2() {
        return Result.ok("查询成功");
    }

    /**
     * 多sheet表-导入
     */
    @Log(name = "多sheet表-导入", type = LogOperType.IMPORT, recordParams = false, recordResult = true)
    @PostMapping("/importData")
    public Result importData(@RequestPart("file") MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.createImport(file.getInputStream());
        ReadRetVo<TestSheetAVo> sheetA = ExcelUtil.readSheet("班级信息", TestSheetAVo.class, true);
        ReadRetVo<TestSheetBVo> sheetB = ExcelUtil.readSheet("学生信息", TestSheetBVo.class, true);
        ExcelUtil.finishRead(reader, sheetA, sheetB);

        ExcelResult<TestSheetAVo> sheetAResult = ExcelUtil.transResult(sheetA);
        ExcelResult<TestSheetBVo> sheetBResult = ExcelUtil.transResult(sheetB);

        List<TestSheetAVo> sheetAList = sheetAResult.getList();
        List<TestSheetBVo> sheetBList = sheetBResult.getList();
        return Result.ok("成功");
    }

    /**
     * 多sheet表-导出
     */
    @Log(name = "多sheet表-导出", type = LogOperType.OTHER, recordParams = true, recordResult = false)
    @PostMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        ExcelWriter writer = ExcelUtil.createExport(response, "多sheet表测试");

        List<TestSheetAVo> sheetAList = new ArrayList<>();
        TestSheetAVo sheetA1 = new TestSheetAVo();
        sheetA1.setClassName("奋进班");
        sheetA1.setClassNum("89");
        sheetA1.setType("0");
        sheetA1.setRemark("位于3楼");
        sheetAList.add(sheetA1);
        TestSheetAVo sheetA2 = new TestSheetAVo();
        sheetA2.setClassName("娇子班");
        sheetA2.setClassNum("70");
        sheetA2.setType("1");
        sheetA2.setRemark("位于5楼");
        sheetAList.add(sheetA2);
        ExcelUtil.writeSheet(writer, "班级信息", sheetAList, TestSheetAVo.class);

        List<TestSheetBVo> sheetBList = new ArrayList<>();
        TestSheetBVo sheetB1 = new TestSheetBVo();
        sheetB1.setStudentName("张三");
        sheetB1.setStudentAge("21");
        sheetB1.setType("0");
        sheetB1.setRemark("位于3楼");
        sheetBList.add(sheetB1);
        TestSheetBVo sheetB2 = new TestSheetBVo();
        sheetB2.setStudentName("王苗苗");
        sheetB2.setStudentAge("19");
        sheetB2.setType("1");
        sheetB2.setRemark("位于5楼");
        sheetBList.add(sheetB2);
        ExcelUtil.writeSheet(writer, "学生信息", sheetBList, TestSheetBVo.class);

        ExcelUtil.finishWrite(writer);
    }

}
