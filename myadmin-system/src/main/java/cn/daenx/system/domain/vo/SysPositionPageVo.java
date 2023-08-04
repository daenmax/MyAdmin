package cn.daenx.system.domain.vo;

import cn.daenx.common.vo.BasePageVo;
import lombok.Data;

@Data
public class SysPositionPageVo extends BasePageVo {
    private String name;
    private String code;
    private String summary;
    private String status;
    private String remark;
}
