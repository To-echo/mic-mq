package com.mic.mq.remote.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author tianp
 **/
@Service
public class MessageRecoverTask {
    /**
     * 执行本地事务->commit 阶段
     * 1.本地事务执行失败
     * 2.commit 阶段失败
     */
    @Scheduled(cron = "")
    private void run() {

    }
}
