package com.mic.common.dto;

/**
 * @author tianp
 **/
public class ProducerMessageDTO {
    private Object id;

    private String topic;

    private String tag;

    private String pubKey;

    private String pubGroup;

    private Integer sendType;

    private String body;


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getPubGroup() {
        return pubGroup;
    }

    public void setPubGroup(String pubGroup) {
        this.pubGroup = pubGroup;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProducerMessageDTO{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", tag='" + tag + '\'' +
                ", pubKey='" + pubKey + '\'' +
                ", pubGroup='" + pubGroup + '\'' +
                ", sendType=" + sendType +
                ", body='" + body + '\'' +
                '}';
    }
}
