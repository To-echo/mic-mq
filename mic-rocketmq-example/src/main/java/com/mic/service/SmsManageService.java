package com.mic.service;

import com.mic.common.annotation.MQProduce;
import com.mic.common.mq.MessageTypeEnum;
import com.mic.common.mq.ProducerGroupDict;
import com.mic.common.dto.ProducerMessageDTO;
import com.mic.common.mq.SendTypeEnum;
import org.springframework.stereotype.Service;

/**
 * @author tianp
 **/
@Service
public class SmsManageService {

    @MQProduce(group = ProducerGroupDict.TEST, type = MessageTypeEnum.USER_LOGIN, send = SendTypeEnum.CONFIRM)
    public void sendSms(ProducerMessageDTO dto) {
        throw new RuntimeException();
    }
}
