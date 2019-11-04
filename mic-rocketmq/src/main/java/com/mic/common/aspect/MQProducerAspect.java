package com.mic.common.aspect;

import com.mic.common.annotation.MQProduce;
import com.mic.common.bean.ResponseVO;
import com.mic.common.handler.TransactionHandler;
import com.mic.common.mq.MessageTypeEnum;
import com.mic.common.mq.SendTypeEnum;
import com.mic.common.dto.ProducerMessageDTO;
import com.mic.common.service.RocketMqProducerService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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
public class MQProducerAspect implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private RocketMqProducerService rocketMqProducerService;

    private TransactionHandler transactionHandler;

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
            //1.预提交
            ResponseVO responseVO = transactionHandler.prepare(dto);
            if (!responseVO.isSuccess()) {
                System.out.println("预提交失败");
                throw new RuntimeException("预提交失败");
            }
            dto.setId(responseVO.getData());
            try {
                result = joinPoint.proceed();
                //TODO 需要对业务逻辑返回的结果进行校验
                //2.提交消息（commit）
                if (transactionHandler.commit(responseVO.getData())) {
                    rocketMqProducerService.send(dto);
                }
            } catch (Exception e) {
                transactionHandler.rollback(dto);
                e.printStackTrace();
            }
        } else {
            result = joinPoint.proceed();
            //普通消息
            rocketMqProducerService.send(dto);
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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        this.transactionHandler = Optional.ofNullable(context.getBean(TransactionHandler.class)).orElse(new TransactionHandler() {
            @Override
            public ResponseVO prepare(ProducerMessageDTO dto) {
                return null;
            }

            @Override
            public boolean commit(Object identity) {
                return false;
            }

            @Override
            public void rollback(ProducerMessageDTO dto) {

            }
        });
    }

    public RocketMqProducerService getRocketMqProducerService() {
        return rocketMqProducerService;
    }

    public void setRocketMqProducerService(RocketMqProducerService rocketMqProducerService) {
        this.rocketMqProducerService = rocketMqProducerService;
    }

    public TransactionHandler getTransactionHandler() {
        return transactionHandler;
    }

    public void setTransactionHandler(TransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }
}
