package cn.daenx.modules.system.domain.dto.sysNotice;

import cn.daenx.framework.common.domain.dto.BasePageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysNoticePageDto extends BasePageDto {
    private String title;
    private String type;
    private String status;
    private String remark;
    private String createName;
}
