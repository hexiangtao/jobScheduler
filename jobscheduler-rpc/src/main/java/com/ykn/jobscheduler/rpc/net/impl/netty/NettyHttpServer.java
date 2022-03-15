package com.ykn.jobscheduler.rpc.net.impl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.*;

/**
 * @author hexiangtao
 * @date 2022/3/15 21:50
 **/
public class NettyHttpServer implements RejectedExecutionHandler, ThreadFactory, Server {

    private static final int DEFAULT_CORE_ZIE = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_MAX_POOL_SIZE = DEFAULT_CORE_ZIE * 2;

    private final ThreadPoolExecutor threadPoolExecutor;
    private final int port;

    public static void main(String[] args) {
        Server server = new NettyHttpServer(80);
        server.start();
    }

    @Override
    public void start() {
        Thread thread = new Thread(() -> doStart());
        thread.setName("nettyServer");
        thread.start();
        System.out.println("server started");

    }

    public NettyHttpServer(int port) {
        this(DEFAULT_CORE_ZIE, DEFAULT_MAX_POOL_SIZE, 50, 1000, port);
    }

    public NettyHttpServer(int corePoolSize, int maxPoolSize, int keepAliveTime, int queueCapacity, int port) {
        this.port = port;
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueCapacity);
        this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, queue, this, this);

    }


    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        throw new RuntimeException("server Thread pool is EXHAUSTED!");
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

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }

}
