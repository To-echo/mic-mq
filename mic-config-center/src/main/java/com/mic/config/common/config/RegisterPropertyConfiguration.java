package com.mic.config.common.config;

import com.mic.config.common.utils.ClassUtils;
import com.mic.config.common.context.MicConfigLocator;
import com.mic.config.common.context.MicZkConfigProperties;
import com.mic.config.common.zk.ZkRegisterOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.bind.PropertySourcesPropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author tianp
 **/
@Configuration
@EnableConfigurationProperties(MicZkConfigProperties.class)
@ConditionalOnProperty(value = "mic.zk.enable", havingValue = "true")
public class RegisterPropertyConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    private static final String BOOTSTRAP_NAME = "bootstrap";
    private static final String ZK_PREFIX = "mic.zk.";
    private MicZkConfigProperties properties;
    private List<MicConfigLocator> micConfigLocators;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        initDependence(environment);
        ZkRegisterOperation.init(properties);
        MutablePropertySources mutablePropertySources = environment.getPropertySources();
        CompositePropertySource composite = new CompositePropertySource(BOOTSTRAP_NAME);
        micConfigLocators.forEach(e -> {
            composite.addPropertySource(e.locate(environment));
        });
        //如果包含配置则更新
        if (!CollectionUtils.isEmpty(composite.getPropertySources())) {
            mutablePropertySources.remove(BOOTSTRAP_NAME);
        }
        mutablePropertySources.addFirst(composite);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private void initDependence(ConfigurableEnvironment environment) {
        initZkProperties(environment);
        if (properties.isEnable()) {
            setMicConfigLocator(ClassUtils.getClassCollection(MicConfigLocator.class));
        }
    }

    public void setMicConfigLocator(
            Collection<? extends MicConfigLocator> initializers) {
        this.micConfigLocators = new ArrayList<MicConfigLocator>();
        this.micConfigLocators.addAll(initializers);
    }

    private void initZkProperties(ConfigurableEnvironment environment) {
        //RelaxedPropertyResolver
        MicZkConfigProperties properties = new MicZkConfigProperties();
        RelaxedDataBinder binder = new RelaxedDataBinder(properties, ZK_PREFIX);
        binder.bind(new PropertySourcesPropertyValues(environment.getPropertySources()));
//        RelaxedPropertyResolver relaxedPropertyResolver = new RelaxedPropertyResolver(environment,"mic.zk.");
        this.properties = properties;
    }
}
