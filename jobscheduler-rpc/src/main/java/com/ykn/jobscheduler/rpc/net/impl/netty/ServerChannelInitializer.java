package com.ykn.jobscheduler.rpc.net.impl.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hexiangtao
 * @date 2022/3/15 22:14
 **/
public class ServerChannelInitializer extends ChannelInitializer {

    private ThreadPoolExecutor threadPoolExecutor;

    public ServerChannelInitializer(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(0, 0, 90, TimeUnit.SECONDS));
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpRequestEncoder());
        pipeline.addLast(new HttpObjectAggregator(5 * 1024 * 1024));
        pipeline.addLast(new NettyHttpServerHandler(threadPoolExecutor));

    }
}
