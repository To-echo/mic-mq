package com.mic.common.dto;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.Random;

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

    public Message transferTo() throws UnsupportedEncodingException {
        Random r = new Random();
        Message message = new Message();
        message.setBody(body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        message.setTags(this.tag);
        message.setTopic(this.topic);
        message.setKeys(this.pubKey);
        return message;
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
