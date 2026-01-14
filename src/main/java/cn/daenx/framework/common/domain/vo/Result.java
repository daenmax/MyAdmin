package cn.daenx.framework.common.domain.vo;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.hutool.http.HttpStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果
 */
@Data
public class Result<T> implements Serializable {

    private int code;
    private boolean success;
    private String msg;
    private T data;
    private long timestamp = System.currentTimeMillis();

    public static <T> Result<T> ok() {
        Result<T> result = new Result<>();
        result.setCode(HttpStatus.HTTP_OK);
        result.setMsg(CommonConstant.MSG_200);
        result.setSuccess(true);
        return result;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(HttpStatus.HTTP_OK);
        result.setMsg(CommonConstant.MSG_200);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> ok(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(HttpStatus.HTTP_OK);
        result.setMsg(msg);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error() {
        Result<T> result = new Result<>();
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMsg(CommonConstant.MSG_500);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> error(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMsg(msg);
        result.setSuccess(false);
        result.setData(data);
        return result;
    }
}
