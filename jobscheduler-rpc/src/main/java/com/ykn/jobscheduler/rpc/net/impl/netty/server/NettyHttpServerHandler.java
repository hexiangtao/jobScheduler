package com.ykn.jobscheduler.rpc.net.impl.netty.server;

import com.alibaba.fastjson.JSON;
import com.ykn.jobscheduler.rpc.RpcRequest;
import com.ykn.jobscheduler.rpc.RpcResponse;
import com.ykn.jobscheduler.rpc.codec.Serializer;
import com.ykn.jobscheduler.rpc.codec.impl.JsonSerializer;
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
        String uri = msg.uri();
        String content = msg.content().toString(StandardCharsets.UTF_8);
        String method = msg.method().name();
        boolean keepAlive = HttpUtil.isKeepAlive(msg);
        String requestId = msg.headers().get(RpcRequest.HEADER_NAME_REQUEST_ID);
        String timestampStr = msg.headers().get(RpcRequest.HEADER_TIMESTAMP);
        Long timestamp = timestampStr != null && timestampStr.trim().length() > 0 ? Long.parseLong(timestampStr) : null;
        String accessToken = msg.headers().get(RpcRequest.HEADER_ACCESS_TOKEN);
        RpcRequest<String> req = new RpcRequest<>(uri, method, keepAlive, requestId, timestamp, accessToken);
        req.setBody(content);
        serverHandlerExecutor.execute(() -> process(ctx, req));
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

    private void process(ChannelHandlerContext ctx, RpcRequest<String> request) {
        byte[] responseBytes = serializer.serialize(new RpcResponse<>(request.getRequestId(), "test", request.getBody()));
        writeResponse(ctx, request.isKeepAlive(), responseBytes);
    }

    private void writeResponse(ChannelHandlerContext ctx, boolean keepAlive, byte[] responseBytes) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(responseBytes));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json;charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        if (keepAlive) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        ctx.writeAndFlush(response);
    }
}
