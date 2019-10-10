package com.mic.common.constans;

public enum MessageTypeEnum {
    PRODUCER(10, "生产者"),
    CONSUMER(20, "消费者")
    ;
    private int messageType;
    private String value;

    MessageTypeEnum(int messageType, String value) {
        this.messageType = messageType;
        this.value = value;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
