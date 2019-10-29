package com.mic.config.common.config;

import com.mic.config.common.context.ZkConfigLocator;
import com.mic.config.common.utils.ClassUtils;
import com.mic.config.common.context.MicConfigLocator;
import com.mic.config.common.properties.MicZkConfigProperties;
import com.mic.config.common.zk.ZkRegisterOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.bind.PropertySourcesPropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
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

    public static final String BOOTSTRAP_NAME = "bootstrap";
    public static final String ZK_PREFIX = "mic.zk.";
    private MicZkConfigProperties properties;
    private List<MicConfigLocator> micConfigLocators;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        loadDependence(environment);
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
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 加载所有依赖项入口
     *
     * @param environment
     */
    private void loadDependence(ConfigurableEnvironment environment) {
        initZkProperties(environment);

        if (properties.isEnable()) {
            Collection<MicConfigLocator> classCollection = ClassUtils.getClassCollection(MicConfigLocator.class);
            classCollection.add(micConfigLocator());
            //加载所有 MicConfigLocator
            setMicConfigLocator(classCollection);
        }
    }

    /**
     * 初始化配置中心zk
     *
     * @param environment
     */
    private void initZkProperties(ConfigurableEnvironment environment) {
        //RelaxedPropertyResolver
        MicZkConfigProperties properties = new MicZkConfigProperties();
        RelaxedDataBinder binder = new RelaxedDataBinder(properties, ZK_PREFIX);
        binder.bind(new PropertySourcesPropertyValues(environment.getPropertySources()));
//        RelaxedPropertyResolver relaxedPropertyResolver = new RelaxedPropertyResolver(environment,"mic.zk.");
        this.properties = properties;
        ZkRegisterOperation.init(properties);
    }

    public void setMicConfigLocator(
            Collection<? extends MicConfigLocator> initializers) {
        this.micConfigLocators = new ArrayList<MicConfigLocator>();
        this.micConfigLocators.addAll(initializers);
    }

    @Bean
    public MicConfigLocator micConfigLocator() {
        return new ZkConfigLocator();
    }
}
