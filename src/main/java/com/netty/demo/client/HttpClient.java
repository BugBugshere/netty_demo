package com.netty.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class HttpClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 9099;
        //线程组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            //启动辅助类
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pp = socketChannel.pipeline();
                            pp.addLast(new HttpClientCodec());
                            pp.addLast(new HttpObjectAggregator(65536));
                            //增加客户端处理逻辑
                            pp.addLast(new HttpClientHandler());
                        }
                    });

            // 启动客户端.
            ChannelFuture f = bootstrap.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
