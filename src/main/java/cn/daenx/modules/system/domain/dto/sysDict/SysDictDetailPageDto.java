package cn.daenx.modules.system.domain.dto.sysDict;

import cn.daenx.framework.common.domain.dto.BasePageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictDetailPageDto extends BasePageDto {
    private String dictCode;
    private String label;
    private String value;
    private String status;
    private String remark;
}
