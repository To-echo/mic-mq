package com.mic.service;

import com.mic.common.annotation.MQProduce;
import com.mic.common.constans.MessageTypeEnum;
import com.mic.common.constans.ProducerGroupDict;
import com.mic.common.dto.ProducerMessageDTO;
import org.springframework.stereotype.Service;

/**
 * @author tianp
 **/
@Service
public class SmsManageService {

    @MQProduce(group = ProducerGroupDict.TEST, type = MessageTypeEnum.USER_LOGIN)
    public void sendSms(ProducerMessageDTO dto) {
    }
}
