package cn.daenx.data.system.domain.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class SysRoleUpdAuthUserVo {
    @NotBlank(message = "角色ID不能为空")
    private String roleId;
    @Size(min = 1, message = "最少选择一个用户")
    private List<String> userIds;
}
