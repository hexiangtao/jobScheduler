package com.ykn.jobscheduler.rpc.net.impl.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author hexiangtao
 * @date 2022/4/17 15:48
 **/
public class StringToIntegerDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        out.add(msg.length());
    }
}
