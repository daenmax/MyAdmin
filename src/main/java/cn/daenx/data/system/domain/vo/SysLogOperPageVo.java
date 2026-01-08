package cn.daenx.data.system.domain.vo;

import cn.daenx.framework.common.vo.BasePageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogOperPageVo extends BasePageVo {

    /**
     * 操作名称
     */
    private String name;

    /**
     * 操作类型，0=其他，1=新增，2=删除，3=修改，4=查询，5=导入，6=导出，7=上传，8=下载
     */
    private String type;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestType;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求者IP
     */
    private String requestIp;

    /**
     * 请求者地址
     */
    private String requestLocation;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 响应结果
     */
    private String responseResult;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 请求结果，0=成功，1=失败
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
