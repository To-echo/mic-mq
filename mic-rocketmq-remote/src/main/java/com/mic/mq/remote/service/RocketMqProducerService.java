package com.mic.mq.remote.service;

import com.mic.common.dto.ProducerMessageDTO;
import com.mic.mq.remote.factory.MQProducerFactory;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author tianp
 **/
@Service
public class RocketMqProducerService {

    public void send(ProducerMessageDTO dto) {
        try {
            DefaultMQProducer defaultMQProducer = MQProducerFactory.get(dto.getPubGroup());
            if (Objects.isNull(defaultMQProducer)) {
                return;
            }
            Message message = new Message();
            message.setTopic(dto.getTopic());
            //。。
            defaultMQProducer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
