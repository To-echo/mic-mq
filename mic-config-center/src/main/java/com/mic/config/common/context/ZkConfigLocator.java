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

    public static final String DATA_PREFIX = "mic.zk.data";

    @Override
    public PropertySource<?> locate(Environment environment) {
        ZkRegisterOperation instance = ZkRegisterOperation.getInstance();
        String data = instance.getData(environment.getProperty(DATA_PREFIX));
        Map<String, Object> source = new HashMap<>();
        String[] split = data.split(";");
        for (String var0 : split) {
            String[] var1 = var0.split("=");
            source.put(var1[0], var1[1]);

        }
        MapPropertySource propertySource = new MapPropertySource("zk-config-server", source);
        return propertySource;
    }
}
