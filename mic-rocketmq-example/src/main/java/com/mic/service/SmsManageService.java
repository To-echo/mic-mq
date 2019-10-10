package com.mic.service;

import com.mic.common.annotation.MQProducer;
import com.mic.common.dto.ProducerMessageDTO;
import com.mic.common.service.RocketMqProducerService;
import com.mic.common.factory.MQProducerFactory;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author tianp
 **/
@Service
public class SmsManageService {

    @MQProducer(group = "producer-send",topic = "topic-test")
    public void sendSms(ProducerMessageDTO dto) {
    }
}
