package com.ykn.jobscheduler.rpc.net.impl.netty;

import com.ykn.jobscheduler.rpc.RpcRequest;
import com.ykn.jobscheduler.rpc.RpcResponse;
import com.ykn.jobscheduler.rpc.codec.Serializer;
import com.ykn.jobscheduler.rpc.codec.impl.JsonSerializer;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.timeout.IdleStateEvent;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author hexiangtao
 * @date 2022/3/15 21:54
 **/
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final ThreadPoolExecutor serverHandlerExecutor;

    private final Serializer serializer;

    private static final Serializer DEFAULT_SERIALIZER = new JsonSerializer();


    public NettyHttpServerHandler(ThreadPoolExecutor serverHandlerExecutor) {
        this.serverHandlerExecutor = serverHandlerExecutor;
        this.serializer = DEFAULT_SERIALIZER;
    }

    public NettyHttpServerHandler(ThreadPoolExecutor serverHandlerExecutor, Serializer serializer) {
        this.serverHandlerExecutor = serverHandlerExecutor;
        this.serializer = serializer;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        serverHandlerExecutor.execute(() -> process(ctx, msg));
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.channel().close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    private void process(ChannelHandlerContext ctx, FullHttpRequest msg) {
        byte[] data = ByteBufUtil.getBytes(msg.content());
        //TODO
        RpcRequest request = serializer.deserialize(data, RpcRequest.class);
        byte[] responseBytes = serializer.serialize(new RpcResponse("test", "test", "test".getBytes(StandardCharsets.UTF_8)));
        boolean keepAlive = HttpUtil.isKeepAlive(msg);
        writeResponse(ctx, keepAlive, responseBytes);
    }

    private void writeResponse(ChannelHandlerContext ctx, boolean keepAlive, byte[] responseBytes) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(responseBytes));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        if (keepAlive) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        ctx.writeAndFlush(response);
    }
}
