package cn.daenx.modules.system.domain.dto.sysJob;

import cn.daenx.framework.common.domain.dto.BasePageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class SysJobLogPageDto extends BasePageDto {

    /**
     * 日志ID
     */
    private String id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 执行结果，0=成功，1=失败
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
