package cn.daenx.common.vo.system.other;

import cn.daenx.common.vo.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 接口限制
 */
@Data
public class SysApiLimitVo extends BaseEntity implements Serializable {
    private String id;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口uri
     */
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
