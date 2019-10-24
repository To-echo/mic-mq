package com.mic.config.common.context;

import com.mic.config.common.zk.ZkRegisterOperation;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tianp
 **/
public class ZkConfigLocator implements MicConfigLocator {

    @Override
    public PropertySource<?> locate(Environment environment) {
        ZkRegisterOperation instance = ZkRegisterOperation.getInstance();
        Map<String, Object> source = new HashMap<>();
        source.put("abc", "123");
        MapPropertySource propertySource = new MapPropertySource("zk-config-server", source);
        return propertySource;
    }
}
