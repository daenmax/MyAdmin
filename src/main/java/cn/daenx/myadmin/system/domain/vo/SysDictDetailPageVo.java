package cn.daenx.myadmin.system.domain.vo;

import cn.daenx.myadmin.common.vo.BasePageVo;
import lombok.Data;

@Data
public class SysDictDetailPageVo extends BasePageVo {
    private String dictCode;
    private String label;
    private String value;
    private String status;
    private String remark;
}
