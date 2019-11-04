package com.mic.common.bean;

import com.mic.common.dict.BusinessCode;

/**
 * @author tianp
 **/
public class ResponseVO<T> {
    /**
     * 业务状态码
     */
    private Integer code;
    /**
     * 错误提示信息
     */
    private String errorTip;
    /**
     * 数据
     */
    private T data;

    public ResponseVO(Integer code, String errorTip, T data) {
        this.code = code;
        this.errorTip = errorTip;
        this.data = data;
    }
    public ResponseVO(Integer code) {
        this.code = code;
    }
    public ResponseVO(Integer code, T data) {
        this.code = code;
        this.data = data;
    }
    public ResponseVO(Integer code, String tips) {
        this.code = code;
        this.errorTip = tips;
    }

    public static ResponseVO success(Object data) {
        return new ResponseVO<>(BusinessCode.SUCCESS, data);
    }
    public static ResponseVO success() {
        return new ResponseVO<>(BusinessCode.SUCCESS);
    }
    public static ResponseVO error(String tips) {
        return new ResponseVO<>(BusinessCode.FAILED, tips);
    }

    public boolean isSuccess() {
        return BusinessCode.SUCCESS.equals(code);
    }

    @Override
    public String toString() {
        return "ResponseVO{" +
                "code=" + code +
                ", errorTip='" + errorTip + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorTip() {
        return errorTip;
    }

    public void setErrorTip(String errorTip) {
        this.errorTip = errorTip;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
