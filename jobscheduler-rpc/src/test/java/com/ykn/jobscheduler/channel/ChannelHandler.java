package com.ykn.jobscheduler.channel;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author hexiangtao
 * @date 2022/4/5 12:05
 **/
public interface ChannelHandler {


    /**
     * Gets called after the ChannelHandler was added to the actual context, and it's ready to  handle events
     *
     * @param ctx
     */
    void handlerAdded(ChannelHandlerContext ctx);


    /**
     * Get called after the ChannelHandler was removed from the actual context, and  it doesn't  handle events anymore
     *
     * @param ctx
     */
    void handlerRemoved(ChannelHandlerContext ctx);


    /**
     * Gets called if a Throwable was thrown.
     *
     * @param ctx
     * @param throwable
     */
    void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable);



}
