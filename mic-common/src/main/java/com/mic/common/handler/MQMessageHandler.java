package com.mic.common.handler;

import com.mic.common.bean.ResponseVO;
import com.mic.common.dto.ProducerMessageDTO;

/**
 * @author tianp
 **/
public interface MQMessageHandler {
    /**
     * 直接提交
     *
     * @param dto
     */
    void directSend(ProducerMessageDTO dto);

    /**
     * 1.预提交
     *
     * @param dto 发布消息dto
     * @return
     */
    ResponseVO prepare(ProducerMessageDTO dto);

    /**
     * 2.提交
     *
     * @param dto 标识
     * @return
     */
    void commit(ProducerMessageDTO dto);

    /**
     * 回滚
     *
     * @param dto 发布消息dto
     */
    void rollback(ProducerMessageDTO dto);
}
