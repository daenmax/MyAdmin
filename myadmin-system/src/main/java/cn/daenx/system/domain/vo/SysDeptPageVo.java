package cn.daenx.system.domain.vo;

import cn.daenx.common.vo.BasePageVo;
import lombok.Data;

@Data
public class SysDeptPageVo extends BasePageVo {
    private String id;
    private String name;
    private String code;
    private String summary;
    private String remark;
    private String status;
}
