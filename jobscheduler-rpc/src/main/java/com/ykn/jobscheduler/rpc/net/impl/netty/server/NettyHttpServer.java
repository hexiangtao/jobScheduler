package com.ykn.jobscheduler.rpc.net.impl.netty.server;

import com.ykn.jobscheduler.rpc.net.server.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hexiangtao
 * @date 2022/3/15 21:50
 **/
public class NettyHttpServer implements Server {

    private static final int DEFAULT_CORE_ZIE = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_MAX_POOL_SIZE = DEFAULT_CORE_ZIE * 2;

    private final ThreadPoolExecutor threadPoolExecutor;
    private final int port;


    public NettyHttpServer(int port) {
        this(DEFAULT_CORE_ZIE, DEFAULT_MAX_POOL_SIZE, 50, 1000, port);
    }

    public NettyHttpServer(ThreadPoolExecutor threadPoolExecutor, int port) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.port = port;
    }

    public NettyHttpServer(int corePoolSize, int maxPoolSize, int keepAliveTime, int queueCapacity, int port) {
        this.port = port;
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueCapacity);
        DefaultRejectedExecutionHandler rejectedExecutionHandler = new DefaultRejectedExecutionHandler();
        ThreadFactory tf = new ServerThreadFactory();
        this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, queue, tf, rejectedExecutionHandler);
    }

    @Override
    public void start() {
        Thread thread = new Thread(() -> doStart());
        thread.setName("nettyServer");
        thread.start();
        System.out.println("server started");

    }

    private void doStart() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            bindAndSync(bossGroup, workerGroup);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shutdownGracefully(bossGroup, workerGroup);
        }
    }


    private void bindAndSync(EventLoopGroup bossGroup, EventLoopGroup workerGroup) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ServerChannelInitializer(threadPoolExecutor));
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture future = serverBootstrap.bind(port).sync();
        future.channel().closeFuture().sync();
    }


    private void shutdownGracefully(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        try {
            threadPoolExecutor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
