package com.mic.common.constans;

/**
 * @author tianp
 **/

public enum MessageStatusEnum {
    WAIT_SEND(30, "待确认"),
    HAS_SEND(31, "已发送");

    private int code;
    private String desc;

    MessageStatusEnum(int code, String desc) {
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
