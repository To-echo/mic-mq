package com.mic.config.common.event;

import org.apache.curator.framework.recipes.cache.NodeCache;

/**watch 监听处理的事件
 * @author tianp
 **/
public interface Processor {
    /**
     *
     * @param cache
     */
    void process(NodeCache cache);
}
