/*
package com.mic.service.mq;

import com.mic.common.handler.TransactionHandler;
import com.mic.common.bean.ResponseVO;
import com.mic.common.mq.MessageStatusEnum;
import com.mic.common.dto.ProducerMessageDTO;
import com.mic.entity.PublishMessageEntity;
import com.mic.mapper.MessageMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

*/
/**
 * @author tianp
 **//*

@Component
public class TransactionMessageHandler implements TransactionMessageHandler {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public ResponseVO prepare(ProducerMessageDTO dto) {
        PublishMessageEntity entity = new PublishMessageEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setStage(MessageStatusEnum.WAIT_SEND.getCode());
        entity.setPubGroup(dto.getPubGroup());
        messageMapper.insert(entity);
        return ResponseVO.success(entity.getId());
    }

    @Override
    public boolean commit(Object identity) {
        messageMapper.update((Integer) identity, MessageStatusEnum.HAS_SEND.getCode());
        return true;
    }

    @Override
    public void rollback(ProducerMessageDTO dto) {
        messageMapper.update((Integer) dto.getId(), MessageStatusEnum.ROLLBACK.getCode());
    }
}
*/
