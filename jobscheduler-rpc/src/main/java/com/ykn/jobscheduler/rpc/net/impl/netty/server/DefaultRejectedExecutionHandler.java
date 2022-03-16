package com.ykn.jobscheduler.rpc.net.impl.netty.server;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author hexiangtao
 * @date 2022/3/16 22:00
 **/
public class DefaultRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        throw new RuntimeException("server Thread pool is EXHAUSTED!");
    }

}
