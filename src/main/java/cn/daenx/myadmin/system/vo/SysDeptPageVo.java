package cn.daenx.myadmin.system.vo;

import cn.daenx.myadmin.common.vo.BasePageVo;
import lombok.Data;

@Data
public class SysDeptPageVo extends BasePageVo {
    private String name;
    private String summary;
    private String status;
}