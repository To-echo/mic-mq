package com.mic.common.mq;

/**
 * @author tianp
 **/
public enum MessageStatusEnum {
    WAIT_SEND(21, "prepare"),
    HAS_SEND(22, "commit"),
    ROLLBACK(23, "rollback");

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
