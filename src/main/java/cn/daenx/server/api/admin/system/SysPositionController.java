package cn.daenx.server.api.admin.system;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.data.system.domain.dto.SysUserPageDto;
import cn.daenx.data.system.domain.po.SysPosition;
import cn.daenx.data.system.domain.vo.*;
import cn.daenx.data.system.service.SysPositionService;
import cn.daenx.data.system.service.SysUserService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/position")
public class SysPositionController {
    @Resource
    private SysPositionService sysPositionService;

    @Resource
    private SysUserService sysUserService;

    /**
     * 列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:position:page")
    @GetMapping(value = "/page")
    public Result page(SysPositionPageVo vo) {
        IPage<SysPosition> page = sysPositionService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:position:export")
    @PostMapping("/exportData")
    public void exportData(SysPositionPageVo vo, HttpServletResponse response) {
        List<SysPosition> list = sysPositionService.getAll(vo);
        ExcelUtil.exportXlsx(response, "岗位", "岗位", list, SysPosition.class);
    }


    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:position:query")
    @GetMapping(value = "/query")
    public Result query(@RequestParam(name = "id", required = true) String id) {
        SysPosition sysPosition = sysPositionService.getInfo(id);
        return Result.ok(sysPosition);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:position:edit")
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody SysPositionUpdVo vo) {
        sysPositionService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:position:add")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody SysPositionAddVo vo) {
        sysPositionService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:position:del")
    @PostMapping("/del")
    public Result del(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysPositionService.deleteByIds(ids);
        return Result.ok();
    }



    /**
     * 查询已分配该岗位的用户列表
     */
    @SaCheckPermission("system:position:page")
    @GetMapping("/allocatedAuthUserPage")
    public Result allocatedAuthUserPage(SysUserPageVo vo, String positionId) {
        if (ObjectUtil.isEmpty(positionId)) {
            throw new MyException("positionId不能为空");
        }
        IPage<SysUserPageDto> page = sysUserService.getUserListByPositionId(vo, positionId);
        return Result.ok(page);
    }

    /**
     * 查询未分配该岗位的用户列表
     */
    @SaCheckPermission("system:position:page")
    @GetMapping("/unallocatedAuthUserPage")
    public Result unallocatedAuthUserPage(SysUserPageVo vo, String positionId) {
        if (ObjectUtil.isEmpty(positionId)) {
            throw new MyException("positionId不能为空");
        }
        IPage<SysUserPageDto> page = sysUserService.getUserListByUnPositionId(vo, positionId);
        return Result.ok(page);
    }

    /**
     * 取消授权用户
     *
     * @param vo
     */
    @SaCheckPermission("system:position:edit")
    @PostMapping("/cancelAuthUser")
    public Result cancelAuthUser(@Validated @RequestBody SysPositionUpdAuthUserVo vo) {
        sysPositionService.cancelAuthUser(vo);
        return Result.ok();
    }

    /**
     * 保存授权用户
     *
     * @param vo
     */
    @SaCheckPermission("system:position:edit")
    @PostMapping("/saveAuthUser")
    public Result saveAuthUser(@Validated @RequestBody SysPositionUpdAuthUserVo vo) {
        sysPositionService.saveAuthUser(vo);
        return Result.ok();
    }
}
