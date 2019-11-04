package com.mic.common.handler;

import com.mic.common.bean.ResponseVO;
import com.mic.common.dto.ProducerMessageDTO;

/**
 * @author tianp
 **/
public interface TransactionHandler {
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
     * @param identity 标识
     * @return
     */
    boolean commit(Object identity);

    /**
     * 回滚
     *
     * @param dto 发布消息dto
     */
    void rollback(ProducerMessageDTO dto);
}
