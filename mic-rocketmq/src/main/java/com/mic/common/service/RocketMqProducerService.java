package com.mic.common.service;

import com.mic.common.dto.ProducerMessageDTO;
import com.mic.common.factory.MQProducerFactory;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
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
                System.out.println("defaultMQProducer is null");
                return;
            }
            defaultMQProducer.send(dto.transferTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
