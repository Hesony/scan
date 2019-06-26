package com.ticket.scan.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求返回的最外层对象
 */
public class ResultVO<T> implements Serializable {

    /** 错误码. */
    private String code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
