package cn.daenx.system.domain.vo;

import cn.daenx.framework.common.vo.BasePageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysPositionPageVo extends BasePageVo {
    private String name;
    private String code;
    private String summary;
    private String status;
    private String remark;
}
