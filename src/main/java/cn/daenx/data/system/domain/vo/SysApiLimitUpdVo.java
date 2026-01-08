package cn.daenx.data.system.domain.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class SysApiLimitUpdVo {
    @NotBlank(message = "ID不能为空")
    private String id;

    /**
     * 接口名称
     */
    @NotBlank(message = "接口名称不能为空")
    private String apiName;

    /**
     * 接口uri
     */
    @NotBlank(message = "不能为空")
    private String apiUri;

    /**
     * 单个用户次数
     */
    private Integer singleFrequency;

    /**
     * 单个用户时间
     */
    private Integer singleTime;

    /**
     * 单个用户时间单位
     */
    private String singleTimeUnit;

    /**
     * 全部用户次数
     */
    private Integer wholeFrequency;

    /**
     * 全部用户时间
     */
    private Integer wholeTime;

    /**
     * 全部用户时间单位
     */
    private String wholeTimeUnit;

    /**
     * 限制类型，0=限流，1=停用
     */
    @NotBlank(message = "限制类型不能为空")
    private String limitType;

    /**
     * 停用提示，当限制类型=1时，接口返回的提示内容
     */
    private String retMsg;

    /**
     * 限制状态，0=正常，1=停用
     */
    @NotBlank(message = "限制状态不能为空")
    private String status;

    /**
     * 备注
     */
    private String remark;

}
