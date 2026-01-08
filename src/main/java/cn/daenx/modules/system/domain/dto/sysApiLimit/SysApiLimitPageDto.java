package cn.daenx.modules.system.domain.dto.sysApiLimit;

import cn.daenx.framework.common.domain.dto.BasePageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class SysApiLimitPageDto extends BasePageDto {

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口uri
     */
    private String apiUri;

    /**
     * 限制类型，0=限流，1=停用
     */
    private String limitType;

    /**
     * 停用提示，当限制类型=1时，接口返回的提示内容
     */
    private String retMsg;

    /**
     * 限制状态，0=正常，1=停用
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
