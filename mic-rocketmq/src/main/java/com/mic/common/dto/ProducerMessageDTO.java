package com.mic.common.dto;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @author tianp
 **/
public class ProducerMessageDTO {

    private String topic;

    private String tag;

    private String key;

    private String group;

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
        message.setTags("A");
        message.setTopic(this.topic);
        message.setKeys(body + r.nextInt(100));
        return message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static ProducerMessageDTO init(String body){
        ProducerMessageDTO dto =  new ProducerMessageDTO();
        dto.setBody(body);
        return dto;
    }
}
