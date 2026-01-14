package cn.daenx.server.api.admin.system;

import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionAddDto;
import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionPageDto;
import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionUpdAuthUserDto;
import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionUpdDto;
import cn.daenx.modules.system.domain.dto.sysUser.SysUserPageDto;
import cn.daenx.modules.system.domain.po.SysPosition;
import cn.daenx.modules.system.domain.vo.sysUser.SysUserPageVo;
import cn.daenx.modules.system.service.SysPositionService;
import cn.daenx.modules.system.service.SysUserService;
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
     * @param dto
     * @return
     */
    @SaCheckPermission("system:position:page")
    @GetMapping(value = "/page")
    public Result<IPage<SysPosition>> page(SysPositionPageDto dto) {
        IPage<SysPosition> page = sysPositionService.getPage(dto);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:position:export")
    @PostMapping("/exportData")
    public void exportData(SysPositionPageDto dto, HttpServletResponse response) {
        List<SysPosition> list = sysPositionService.getAll(dto);
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
    public Result<SysPosition> query(@RequestParam(name = "id", required = true) String id) {
        SysPosition sysPosition = sysPositionService.getInfo(id);
        return Result.ok(sysPosition);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:position:edit")
    @GetMapping(value = "/edit")
    public Result<Void> edit(@Validated @RequestBody SysPositionUpdDto dto) {
        sysPositionService.editInfo(dto);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:position:add")
    @PostMapping("/add")
    public Result<Void> add(@Validated @RequestBody SysPositionAddDto dto) {
        sysPositionService.addInfo(dto);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:position:del")
    @GetMapping(value = "/del")
    public Result<Void> del(@RequestBody List<String> ids) {
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
    public Result<IPage<SysUserPageVo>> allocatedAuthUserPage(SysUserPageDto dto, String positionId) {
        if (ObjectUtil.isEmpty(positionId)) {
            throw new MyException("positionId不能为空");
        }
        IPage<SysUserPageVo> page = sysUserService.getUserListByPositionId(dto, positionId);
        return Result.ok(page);
    }

    /**
     * 查询未分配该岗位的用户列表
     */
    @SaCheckPermission("system:position:page")
    @GetMapping("/unallocatedAuthUserPage")
    public Result<IPage<SysUserPageVo>> unallocatedAuthUserPage(SysUserPageDto dto, String positionId) {
        if (ObjectUtil.isEmpty(positionId)) {
            throw new MyException("positionId不能为空");
        }
        IPage<SysUserPageVo> page = sysUserService.getUserListByUnPositionId(dto, positionId);
        return Result.ok(page);
    }

    /**
     * 取消授权用户
     *
     * @param dto
     */
    @SaCheckPermission("system:position:edit")
    @PostMapping("/cancelAuthUser")
    public Result<Void> cancelAuthUser(@Validated @RequestBody SysPositionUpdAuthUserDto dto) {
        sysPositionService.cancelAuthUser(dto);
        return Result.ok();
    }

    /**
     * 保存授权用户
     *
     * @param dto
     */
    @SaCheckPermission("system:position:edit")
    @PostMapping("/saveAuthUser")
    public Result<Void> saveAuthUser(@Validated @RequestBody SysPositionUpdAuthUserDto dto) {
        sysPositionService.saveAuthUser(dto);
        return Result.ok();
    }
}
