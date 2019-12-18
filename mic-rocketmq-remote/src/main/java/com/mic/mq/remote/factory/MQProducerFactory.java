package com.mic.mq.remote.factory;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tianp
 **/
public class MQProducerFactory {
    private MQProducerFactory() {
    }

    private static Map<String, DefaultMQProducer> producerHolder = new ConcurrentHashMap<>();

    public static void put(String groupId, DefaultMQProducer producer) {
        producerHolder.put(groupId, producer);
    }

    public static void rm(String groupId) {
        producerHolder.remove(groupId);
    }

    public static DefaultMQProducer get(String groupId) {
        return producerHolder.get(groupId);
    }
}
