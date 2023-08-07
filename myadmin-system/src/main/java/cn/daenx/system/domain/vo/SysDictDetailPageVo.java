package cn.daenx.system.domain.vo;

import cn.daenx.framework.common.vo.BasePageVo;
import lombok.Data;

@Data
public class SysDictDetailPageVo extends BasePageVo {
    private String dictCode;
    private String label;
    private String value;
    private String status;
    private String remark;
}
