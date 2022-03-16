package com.ykn.jobscheduler.rpc.net.impl.netty.server;

import java.util.concurrent.ThreadFactory;

/**
 * @author hexiangtao
 * @date 2022/3/16 22:01
 **/
public class ServerThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }

}
