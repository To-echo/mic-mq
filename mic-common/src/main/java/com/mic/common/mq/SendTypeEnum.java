package com.mic.common.mq;

/**
 * @author tianp
 **/
public enum SendTypeEnum {
    DIRECT(10, "直接发送"),
    CONFIRM(11, "等待确认")
    ;

    private int code;
    private String desc;


    SendTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
