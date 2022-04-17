package com.ykn.jobscheduler.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author hexiangtao
 * @date 2022/4/4 20:52
 **/
public class MyHandler extends ChannelDuplexHandler {


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }
}
