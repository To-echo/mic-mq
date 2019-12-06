package com.mic.config.common.zk;

import com.mic.config.common.event.Processor;

/**
 * @author tianp
 **/
public interface ZkOperation {

    /**
     * 获取路径下的值
     * @param path 路径
     * @return 值
     */
    String getData(String path);

    /**
     * 循环获取路径下的值
     * @param path 路径
     * @return 值
     */
    String[] getDataForeach(String path);

    /**
     * 注册 watch 监听
     * @param path
     * @return
     */
    boolean registerWatcher(String path, Processor processor);
}
