package cn.daenx.myadmin.common.vo;

import cn.daenx.myadmin.common.constant.CommonConstant;
import cn.hutool.http.HttpStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果
 */
@Data
public class Result implements Serializable {

    private int code;
    private boolean success;
    private String msg;
    private Object data;
    private long timestamp = System.currentTimeMillis();

    public static Result ok() {
        Result result = new Result();
        result.setCode(HttpStatus.HTTP_OK);
        result.setMsg(CommonConstant.MSG_200);
        result.setSuccess(true);
        return result;
    }

    public static Result ok(Object data) {
        Result result = new Result();
        result.setCode(HttpStatus.HTTP_OK);
        result.setMsg(CommonConstant.MSG_200);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static Result ok(String msg, Object data) {
        Result result = new Result();
        result.setCode(HttpStatus.HTTP_OK);
        result.setMsg(msg);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMsg(CommonConstant.MSG_500);
        result.setSuccess(false);
        return result;
    }

    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }

    public static Result error(String msg, Object data) {
        Result result = new Result();
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMsg(msg);
        result.setSuccess(false);
        result.setData(data);
        return result;
    }
}
