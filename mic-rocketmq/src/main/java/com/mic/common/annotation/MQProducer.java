package com.mic.common.annotation;

import com.mic.common.constans.SendTypeEnum;

import java.lang.annotation.*;

/**
 * 发送消息注解
 *
 * @author tianp
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MQProducer {
    String topic() default "";

    String group() default "";

    SendTypeEnum sendType() default SendTypeEnum.DIRECT;
}
