package com.zephyrtoria.schedule.common;

/*
{"code": "",  // 业务状态码：本次请求的业务是否成功，或如果失败是由于什么（不是响应状态码，响应成功不代表业务成功）
"message": "",  // 业务状态码的补充说明、描述（业务状态码可能在不同工程中表示含义不同）
"data": {}}  // 本次响应的数据，包含一个对象
*/

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result() {
    }

    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (null != data) {
            result.setData(data);
        }
        return result;
    }

    protected static <T> Result<T> built(T body, Integer code, String message) {
        Result<T> result = build(body);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    /**
     * 操作成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }
}
