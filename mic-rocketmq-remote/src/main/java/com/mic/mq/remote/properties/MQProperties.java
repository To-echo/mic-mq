package com.mic.mq.remote.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author tianp
 **/
@ConfigurationProperties(prefix = "rocket.mq")
public class MQProperties {
    private String nameSrv;
    private List<MQProducerProperties> producers;
    private List<MQConsumerProperties> consumers;

    public static class MQProducerProperties {

        private String producerGroup="topic-test";
        private int sendMsgTimeout = 3000;
        private int retryTimesWhenSendFailed = 2;

        public String getProducerGroup() {
            return producerGroup;
        }

        public void setProducerGroup(String producerGroup) {
            this.producerGroup = producerGroup;
        }

        public int getSendMsgTimeout() {
            return sendMsgTimeout;
        }

        public void setSendMsgTimeout(int sendMsgTimeout) {
            this.sendMsgTimeout = sendMsgTimeout;
        }

        public int getRetryTimesWhenSendFailed() {
            return retryTimesWhenSendFailed;
        }

        public void setRetryTimesWhenSendFailed(int retryTimesWhenSendFailed) {
            this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
        }
    }

    public static class MQConsumerProperties {
        private String consumerGroup;

        public String getConsumerGroup() {
            return consumerGroup;
        }

        public void setConsumerGroup(String consumerGroup) {
            this.consumerGroup = consumerGroup;
        }
    }

    public String getNameSrv() {
        return nameSrv;
    }

    public void setNameSrv(String nameSrv) {
        this.nameSrv = nameSrv;
    }

    public List<MQProducerProperties> getProducers() {
        return producers;
    }

    public void setProducers(List<MQProducerProperties> producers) {
        this.producers = producers;
    }

    public List<MQConsumerProperties> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<MQConsumerProperties> consumers) {
        this.consumers = consumers;
    }
}
