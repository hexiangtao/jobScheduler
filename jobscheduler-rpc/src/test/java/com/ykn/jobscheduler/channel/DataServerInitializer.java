package com.ykn.jobscheduler.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * @author hexiangtao
 * @date 2022/4/4 20:39
 **/
public class DataServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast("handler", new DataServerHandler());
    }
}
