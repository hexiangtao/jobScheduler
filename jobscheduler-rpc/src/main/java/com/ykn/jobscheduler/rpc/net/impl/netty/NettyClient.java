package com.ykn.jobscheduler.rpc.net.impl.netty;

import com.ykn.jobscheduler.rpc.RpcRequest;
import com.ykn.jobscheduler.rpc.net.Client;

/**
 * @author hexiangtao
 * @date 2022/3/15 21:41
 **/
public class NettyClient implements Client {

    private NettyConnectClient nettyConnectClient;

    public NettyClient(NettyConnectClient nettyConnectClient) {
        this.nettyConnectClient = nettyConnectClient;
    }

    @Override
    public void send(String address, RpcRequest request) {
        this.nettyConnectClient.send(request);
    }
}
