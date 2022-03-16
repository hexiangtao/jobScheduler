package com.ykn.jobscheduler.rpc.net.impl.netty.client;

import com.ykn.jobscheduler.rpc.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * @author hexiangtao
 * @date 2022/3/15 21:42
 **/
public class NettyConnectClient extends ChannelInitializer<SocketChannel> {

    private final EventLoopGroup group;

    private final Channel channel;

    public NettyConnectClient(String host, int port) throws Exception {
        this.group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSctpChannel.class).handler(this);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);
        this.channel = bootstrap.connect(host, port).sync().channel();
        this.channel.closeFuture().sync();


    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpClientCodec());
        p.addLast(new HttpContentCompressor());
        p.addLast(new HttpObjectAggregator(512 * 1024));
        p.addLast(new HttpClientInboundHandler());


    }

    public void send(RpcRequest request) {
        channel.writeAndFlush(request);
    }


}
