package com.mic.common.handler;

import com.mic.common.bean.ResponseVO;
import com.mic.common.dto.ProducerMessageDTO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tianp
 **/
@Service
public class GenericMessageHandler implements MQMessageHandler, InitializingBean {
    public ThreadPoolExecutor poolExecutor;
    public String threadPrefix = "mq-remote-call_%d";

    @Override
    public void directSend(ProducerMessageDTO dto) {
        poolExecutor = new ThreadPoolExecutor(1, 10,
                60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),
                new ThreadFactory() {
                    AtomicInteger ai = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, String.format(threadPrefix, this.ai.incrementAndGet()));
                    }
                }, new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public ResponseVO prepare(ProducerMessageDTO dto) {
        return null;
    }

    @Override
    public void commit(ProducerMessageDTO dto) {
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void rollback(ProducerMessageDTO dto) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
