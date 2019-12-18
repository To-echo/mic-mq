package com.mic.common.aspect;

import com.mic.common.annotation.MQProduce;
import com.mic.common.bean.ResponseVO;
import com.mic.common.dto.ProducerMessageDTO;
import com.mic.common.handler.GenericMessageHandler;
import com.mic.common.mq.MessageTypeEnum;
import com.mic.common.mq.SendTypeEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * @author tianp
 **/
@Aspect
@Component
public class MQProducerAspect {
    @Autowired
    private GenericMessageHandler genericMessageHandler;

    @Pointcut(value = "@annotation(com.mic.common.annotation.MQProduce)")
    public void produce() {
    }


    @Around(value = "produce()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        ProducerMessageDTO dto = Optional.ofNullable(validParam(joinPoint)).orElseThrow(RuntimeException::new);
        MQProduce annotation = getSignature(joinPoint, MQProduce.class);
        MessageTypeEnum type = annotation.type();
        dto.setPubGroup(annotation.group());
        dto.setTopic(type.getTopic());
        dto.setSendType(annotation.send().getCode());
        //事务消息
        if (dto.getSendType().equals(SendTypeEnum.CONFIRM.getCode())) {
            //1.预提交(远程调用)
            ResponseVO responseVO = genericMessageHandler.prepare(dto);
            if (!responseVO.isSuccess()) {
                throw new RuntimeException("预提交失败");
            }
            dto.setId(responseVO.getData());
            try {
                result = joinPoint.proceed();
                //2.提交消息（异步+远程调用）
                genericMessageHandler.commit(dto);
            } catch (Exception e) {
                genericMessageHandler.rollback(dto);
                throw new Exception(e);
            }
        } else {
            result = joinPoint.proceed();
            //普通消息直接发送
            genericMessageHandler.directSend(dto);
        }
        return result;
    }

    private ProducerMessageDTO validParam(ProceedingJoinPoint joinPoint) {
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
        return dto;

    }

    private <T extends Annotation> T getSignature(JoinPoint joinPoint, Class<T> clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(clazz);
    }
}
