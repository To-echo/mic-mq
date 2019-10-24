package com.mic.config.common.zk;

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
}
