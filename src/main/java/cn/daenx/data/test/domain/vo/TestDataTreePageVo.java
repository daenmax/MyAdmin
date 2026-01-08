package cn.daenx.data.test.domain.vo;

import cn.daenx.framework.common.vo.BasePageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TestDataTreePageVo extends BasePageVo {
    private String title;
    private String content;
    private String type;
    private String status;
    private String remark;
}
