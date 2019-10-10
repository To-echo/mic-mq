package com.mic.common.config;

import com.mic.common.factory.MQProducerFactory;
import com.mic.common.properties.MQProperties;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author tianp
 **/
@Component
@EnableConfigurationProperties(value = MQProperties.class)
@ConditionalOnProperty(value = "rocket.mq.name-srv")
public class MQComandLineRunnerImpl implements CommandLineRunner {
    @Autowired
    private MQProperties properties;
    @Autowired
    private ApplicationContext application;

    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) ((AnnotationConfigEmbeddedWebApplicationContext) application).getBeanFactory();
        initProducer(factory);
    }

    /**
     * 初始化 Producer
     *
     * @param factory factory 工厂
     */
    private void initProducer(DefaultListableBeanFactory factory) {
        for (MQProperties.MQProducerProperties prop : properties.getProducers()) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DefaultMQProducer.class);
            builder.setScope(BeanDefinition.SCOPE_SINGLETON);
            builder.addPropertyValue("namesrvAddr", properties.getNameSrv());
            builder.addPropertyValue("producerGroup", prop.getProducerGroup());
            builder.addPropertyValue("sendMsgTimeout", prop.getSendMsgTimeout());
            builder.addPropertyValue("retryTimesWhenSendFailed", prop.getRetryTimesWhenSendFailed());
            builder.setInitMethodName("start");
            builder.setDestroyMethodName("shutdown");
            factory.registerBeanDefinition(prop.getProducerGroup(), builder.getRawBeanDefinition());
            MQProducerFactory.put(prop.getProducerGroup(), (DefaultMQProducer) application.getBean(prop.getProducerGroup()));
        }
    }

    public void initConsumer(DefaultListableBeanFactory factory) {
        for (MQProperties.MQConsumerProperties prop : properties.getConsumers()) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DefaultMQPushConsumer.class);
            builder.setScope(BeanDefinition.SCOPE_SINGLETON);
            builder.addPropertyValue("namesrvAddr", properties.getNameSrv());
            builder.setInitMethodName("start");
            builder.setDestroyMethodName("shutdown");
//            factory.registerBeanDefinition(prop.getProducerGroup(), builder.getRawBeanDefinition());
//            MQProducerFactory.put(prop.getProducerGroup(), (DefaultMQProducer) application.getBean(prop.getProducerGroup()));
        }

    }
//    public static void main(String[] args) throws MQClientException {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
//        consumer.setNamesrvAddr("");
////        consumer.setPullBatchSize(1);
//        consumer.setConsumerGroup("");
//        consumer.subscribe("topic","*");
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//        consumer.setConsumeMessageBatchMaxSize(1);
////        consumer.set
//    }
}
