package com.mic.common.mq;

public enum MessageTypeEnum {
    USER_LOGIN(100,TopicDict.LOGIN, "用户登录，新增积分"),
    CHECK_IN(101, TopicDict.CHECK_IN,"用户签到，新增积分");
    private int messageType;
    private String topic;
    private String desc;

    MessageTypeEnum(int messageType,String topic, String desc) {
        this.messageType = messageType;
        this.topic = topic;
        this.desc = desc;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
