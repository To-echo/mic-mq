package com.mic.config.common.context;

import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

/**
 * @author tianp
 **/
public interface MicConfigLocator {
    /**
     * @param environment the current Environment
     * @return a PropertySource or null if there is none
     */
    PropertySource<?> locate(Environment environment);
}