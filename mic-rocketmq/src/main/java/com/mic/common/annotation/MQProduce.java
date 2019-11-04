package com.mic.common.annotation;

import com.mic.common.mq.MessageTypeEnum;
import com.mic.common.mq.SendTypeEnum;

import java.lang.annotation.*;

/**
 * 发送消息注解
 *
 * @author tianp
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MQProduce {

    String group() default "";

    MessageTypeEnum type();

    SendTypeEnum send() default SendTypeEnum.DIRECT;
}
