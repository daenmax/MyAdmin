package cn.daenx.myadmin.system.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class SysPositionUpdAuthUserVo {
    @NotBlank(message = "岗位ID不能为空")
    private String positionId;
    @Size(min = 1, message = "最少选择一个用户")
    private List<String> userIds;
}
