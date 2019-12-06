package com.mic.config.common.zk;

import com.google.common.base.Charsets;
import com.mic.config.common.event.Processor;
import com.mic.config.common.properties.MicZkConfigProperties;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author tianp
 **/
public class ZkRegisterOperation implements ZkOperation {

    private static volatile ZkRegisterOperation zkRegisterOperation;
    private CuratorFramework cf;

    private ZkRegisterOperation() {
    }

    public static ZkRegisterOperation init(MicZkConfigProperties config) {
        if (zkRegisterOperation == null) {
            synchronized (ZkRegisterOperation.class) {
                if (zkRegisterOperation == null) {
                    zkRegisterOperation = new ZkRegisterOperation();
                    zkRegisterOperation.cf = CuratorFrameworkFactory.builder()
                            .connectString(config.getAddressList())
                            .sessionTimeoutMs(config.getSessionTimeoutMilliseconds())
                            .connectionTimeoutMs(config.getConnectionTimeoutMilliseconds())
                            .retryPolicy(new ExponentialBackoffRetry
                                    (
                                            config.getBaseSleepTimeMilliseconds(),
                                            config.getMaxRetries(),
                                            config.getMaxSleepTimeMilliseconds()))
                            .build();
                }
            }
        }
        zkRegisterOperation.cf.start();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        zkRegisterOperation.cf.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                if (newState.isConnected()) {
                    countDownLatch.countDown();
                }
            }
        });
        try {
            if (!zkRegisterOperation.cf.blockUntilConnected(config.getMaxSleepTimeMilliseconds() * config.getMaxRetries(), TimeUnit.MILLISECONDS)) {
                zkRegisterOperation.cf.close();
                throw new KeeperException.OperationTimeoutException();
            }
            countDownLatch.await(10, TimeUnit.SECONDS);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return zkRegisterOperation;
    }


    public static ZkRegisterOperation getInstance() {
        return zkRegisterOperation;
    }

    @Override
    public String getData(String path) {
        String data;
        try {
            data = new String(cf.getData().forPath(path), Charsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return data;
    }

    @Override
    public String[] getDataForeach(String path) {
        return new String[0];
    }

    @Override
    public boolean registerWatcher(String path, Processor processor) {
        NodeCache nodeCache = new NodeCache(cf, path, false);
        try {
            nodeCache.start(true);
            nodeCache.getListenable().addListener(new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    processor.process(nodeCache);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
