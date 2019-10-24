/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MicZkConfigProperties.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.mic.config.common.context;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tianpeng
 */
@ConfigurationProperties("mic.zk")
public class MicZkConfigProperties {
    private boolean enable = false;
    /**
     * 连接Zookeeper服务器的列表
     * 包括IP地址和端口号
     * 多个地址用逗号分隔
     * 如: host1:2181,host2:2181
     */
    private String addressList = "localhost:2181";

    /**
     * 等待重试的间隔时间的初始值
     * 单位：毫秒
     */
    private int baseSleepTimeMilliseconds = 1000;

    /**
     * 等待重试的间隔时间的最大值
     * 单位：毫秒
     */
    private int maxSleepTimeMilliseconds = 3000;

    /**
     * 最大重试次数
     */
    private int maxRetries = 3;

    /**
     * 连接超时时间
     * 单位：毫秒
     */
    private int connectionTimeoutMilliseconds = 15000;

    /**
     * 会话超时时间
     * 单位：毫秒
     */
    private int sessionTimeoutMilliseconds = 60000;


    public String getAddressList() {
        return addressList;
    }

    public void setAddressList(String addressList) {
        this.addressList = addressList;
    }

    public int getBaseSleepTimeMilliseconds() {
        return baseSleepTimeMilliseconds;
    }

    public void setBaseSleepTimeMilliseconds(int baseSleepTimeMilliseconds) {
        this.baseSleepTimeMilliseconds = baseSleepTimeMilliseconds;
    }

    public int getMaxSleepTimeMilliseconds() {
        return maxSleepTimeMilliseconds;
    }

    public void setMaxSleepTimeMilliseconds(int maxSleepTimeMilliseconds) {
        this.maxSleepTimeMilliseconds = maxSleepTimeMilliseconds;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public int getConnectionTimeoutMilliseconds() {
        return connectionTimeoutMilliseconds;
    }

    public void setConnectionTimeoutMilliseconds(int connectionTimeoutMilliseconds) {
        this.connectionTimeoutMilliseconds = connectionTimeoutMilliseconds;
    }

    public int getSessionTimeoutMilliseconds() {
        return sessionTimeoutMilliseconds;
    }

    public void setSessionTimeoutMilliseconds(int sessionTimeoutMilliseconds) {
        this.sessionTimeoutMilliseconds = sessionTimeoutMilliseconds;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "MicZkConfigProperties{" +
                "enable=" + enable +
                ", addressList='" + addressList + '\'' +
                ", baseSleepTimeMilliseconds=" + baseSleepTimeMilliseconds +
                ", maxSleepTimeMilliseconds=" + maxSleepTimeMilliseconds +
                ", maxRetries=" + maxRetries +
                ", connectionTimeoutMilliseconds=" + connectionTimeoutMilliseconds +
                ", sessionTimeoutMilliseconds=" + sessionTimeoutMilliseconds +
                '}';
    }
}
