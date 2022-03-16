package com.ykn.jobscheduler.rpc.net.impl.netty.client;

import com.ykn.jobscheduler.rpc.RpcRequest;
import com.ykn.jobscheduler.rpc.net.Client;

/**
 * @author hexiangtao
 * @date 2022/3/15 21:41
 **/
public class NettyClient implements Client {

    private NettyConnectClient nettyConnectClient;

    public NettyClient(String host, int port) throws Exception {
        this.nettyConnectClient = new NettyConnectClient(host, port);
    }

    @Override
    public void send(RpcRequest request) {
        this.nettyConnectClient.send(request);
    }
}
