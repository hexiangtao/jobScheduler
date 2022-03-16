package com.ykn.jobscheduler.rpc.test;

import com.ykn.jobscheduler.rpc.net.impl.netty.server.NettyHttpServer;
import com.ykn.jobscheduler.rpc.net.server.Server;

/**
 * @author hexiangtao
 * @date 2022/3/16 22:29
 **/
public class ServerTest {

    public static void main(String[] args) {
        Server server = new NettyHttpServer(80);
        server.start();
    }
}

