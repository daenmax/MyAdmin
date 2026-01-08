package cn.daenx.modules.system.domain.dto.sysPosition;

import cn.daenx.framework.common.domain.dto.BasePageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysPositionPageDto extends BasePageDto {
    private String name;
    private String code;
    private String summary;
    private String status;
    private String remark;
}
