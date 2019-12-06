package com.mic.config.common.config;

import com.mic.config.common.event.Processor;
import com.mic.config.common.zk.ZkRegisterOperation;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author tianp
 **/
public class StatRefreshListener implements ApplicationListener<ContextRefreshedEvent> {
    private RegisterPropertyConfiguration register;

    public StatRefreshListener(RegisterPropertyConfiguration register) {
        this.register = register;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) event.getApplicationContext();
        ZkRegisterOperation.getInstance().registerWatcher(register.getProperties().getData(), new Processor() {
            @Override
            public void process(NodeCache cache) {
                //更新后的配置数据
                //TODO 日志打印
                register.fresh(context.getEnvironment());
            }
        });
    }
}
