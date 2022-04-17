package com.ykn.jobscheduler.channel;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author hexiangtao
 * @date 2022/4/5 12:13
 **/
public interface ChannelInboundHandler extends ChannelHandler {


    /**
     * the Channel  of ChannelHandlerContext  was registered   with its EventLoop
     *
     * @param ctx
     */
    void channelRegistered(ChannelHandlerContext ctx);


    /**
     * the channel of ChannelHandlerContext was unregistered with its' eventLoop
     *
     * @param ctx
     */
    void channelUnRegistered(ChannelHandlerContext ctx);


    void channelActive(ChannelHandlerContext ctx);


    /**
     * the channel of the ChannelHandlerContext was registered is now inactive  and reached its end of lifetime
     *
     * @param ctx
     */
    void channelInActive(ChannelHandlerContext ctx);


    /**
     * invoked when the current channel  has read  a message  from the peer
     */
    void channelRead(ChannelHandlerContext ctx, Object msg);


    /**
     * invoke when the last message read by current read operation  has been consumed by channelRead() if ChannelOption.
     * AUTO_READ is off, no further attempt  to read an inbound data from the current Channel  will be made until
     * ChannelHandlerContext.read() is called.
     *
     * @param ctx
     */
    void channelReadCompleted(ChannelHandlerContext ctx);


    /**
     * Gets called if an user event was triggered
     *
     * @param ctx
     * @param event
     */
    void userEventTriggerred(ChannelHandlerContext ctx, Object event);


    /**
     * Gets called  once the writable  state of  a Channel  changed.   you can check the state  with Channel.isWriteable()
     */
    void channleWritablitityChanned();


}
