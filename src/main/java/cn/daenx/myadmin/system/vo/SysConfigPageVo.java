package cn.daenx.myadmin.system.vo;

import cn.daenx.myadmin.common.vo.BasePageVo;
import lombok.Data;

@Data
public class SysConfigPageVo extends BasePageVo {
    private String name;
    private String key;
    private String value;
    private String type;
    private String status;
    private String remark;
}
