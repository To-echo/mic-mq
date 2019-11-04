package com.mic.common.mq;

/**
 * topic 字典，通过系统名字加下划线再加topic 编码构成
 * systemName_code
 * @author tianp
 **/
public class TopicDict {
    /**
     * 积分系统-用户登录，新增积分
     */
    public static final String LOGIN = SystemDict.INTEGRAL+"_LOGIN";
    /**
     * 积分系统-用户签到，新增积分
     */
    public static final String CHECK_IN=SystemDict.INTEGRAL+"_CHECK_IN";
}
