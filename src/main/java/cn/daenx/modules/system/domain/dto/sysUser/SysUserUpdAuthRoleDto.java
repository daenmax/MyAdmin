package cn.daenx.modules.system.domain.dto.sysUser;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class SysUserUpdAuthRoleDto {
    @NotBlank(message = "用户ID不能为空")
    private String userId;
    private List<String> roleIds;
}
