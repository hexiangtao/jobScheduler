package com.ykn.jobscheduler.channel;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author hexiangtao
 * @date 2022/4/5 12:51
 **/
public class ChannelInboundHandlerAdapter extends ChannelHandlerAdapter implements ChannelInboundHandler {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnRegistered(ChannelHandlerContext ctx) {
        ctx.fireChannelUnregistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.fireChannelActive();

    }

    @Override
    public void channelInActive(ChannelHandlerContext ctx) {
        ctx.fireChannelInactive();

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    }

    @Override
    public void channelReadCompleted(ChannelHandlerContext ctx) {

    }

    @Override
    public void userEventTriggerred(ChannelHandlerContext ctx, Object event) {

    }

    @Override
    public void channleWritablitityChanned() {

    }
}
