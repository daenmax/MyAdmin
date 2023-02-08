package cn.daenx.myadmin.common.vo;

import cn.daenx.myadmin.common.constant.Constant;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果
 */
@Data
public class Result implements Serializable {

    private String code;
    private boolean success;
    private String msg;
    private Object data;
    private long timestamp = System.currentTimeMillis();

    public static Result ok() {
        Result result = new Result();
        result.setCode(Constant.CODE_200);
        result.setMsg(Constant.MSG_200);
        result.setSuccess(true);
        return result;
    }

    public static Result ok(Object data) {
        Result result = new Result();
        result.setCode(Constant.CODE_200);
        result.setMsg(Constant.MSG_200);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static Result ok(String msg, Object data) {
        Result result = new Result();
        result.setCode(Constant.CODE_200);
        result.setMsg(msg);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setCode(Constant.CODE_500);
        result.setMsg(Constant.MSG_500);
        result.setSuccess(false);
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(Constant.CODE_500);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }

    public static Result error(String msg, Object data) {
        Result result = new Result();
        result.setCode(Constant.CODE_500);
        result.setMsg(msg);
        result.setSuccess(false);
        result.setData(data);
        return result;
    }
}
