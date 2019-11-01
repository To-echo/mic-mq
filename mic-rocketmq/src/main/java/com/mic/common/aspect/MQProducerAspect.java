package com.mic.common.aspect;

import com.mic.common.annotation.MQProduce;
import com.mic.common.constans.MessageTypeEnum;
import com.mic.common.constans.SendTypeEnum;
import com.mic.common.dto.ProducerMessageDTO;
import com.mic.common.service.RocketMqProducerService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
    private RocketMqProducerService rocketMqProducerService;

    @Pointcut(value = "@annotation(com.mic.common.annotation.MQProduce)")
    public void produce() {
    }


    @Around(value = "produce()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        ProducerMessageDTO dto = Optional.ofNullable(validParam(joinPoint)).orElseThrow(RuntimeException::new);
        MQProduce annotation = getSignature(joinPoint, MQProduce.class);
        MessageTypeEnum type = annotation.type();
        dto.setGroup(annotation.group());
        dto.setTopic(type.getTopic());
        dto.setSendType(annotation.send().getCode());
        //如果是两阶段提交则需要执行预提交
        if (dto.getSendType().equals(SendTypeEnum.CONFIRM.getCode())) {
            //TODO 1.预提交（prepare）

        }
        result = joinPoint.proceed();
        //TODO 判断 ret 返回值是否存在业务上的失败

        //2.提交消息（commit）
        rocketMqProducerService.send(dto);
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
