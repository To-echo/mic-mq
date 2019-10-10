package com.mic.common.aspect;

import com.mic.common.annotation.MQProducer;
import com.mic.common.dto.ProducerMessageDTO;
import com.mic.common.service.RocketMqProducerService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author tianp
 **/
@Aspect
@Component
public class MQProducerAspect {
    @Autowired
    private RocketMqProducerService rocketMqProducerService;

    @Pointcut("@annotation(com.mic.common.annotation.MQProducer)")
    public void produce() {
    }

    @Around(value = "produce()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("发送消息参数错误！");
        }
        ProducerMessageDTO dto = null;
        for (Object o : args) {
            if (o instanceof ProducerMessageDTO) {
                dto = (ProducerMessageDTO) o;
                break;
            }
        }
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("MessageDTO 不能为空！");
        }

        MQProducer annotation = getSignature(joinPoint);
        dto.setGroup(annotation.group());
        dto.setTopic(annotation.topic());
        dto.setSendType(annotation.sendType().getCode());
        result = joinPoint.proceed();
        //2.发送消息
        //TODO 暂时只支持直接发送
        rocketMqProducerService.send(dto);
        return result;
    }

    private MQProducer getSignature(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(MQProducer.class);
    }
}
