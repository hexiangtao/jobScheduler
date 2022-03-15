package com.ykn.jobscheduler.rpc.net.impl.netty;

import com.ykn.jobscheduler.rpc.RpcRequest;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;

/**
 * @author hexiangtao
 * @date 2022/3/15 21:42
 **/
public class NettyConnectClient {

    private EventLoopGroup group;

    private Channel channel;


    public void send(RpcRequest request) {
        channel.writeAndFlush(request);
    }


}
