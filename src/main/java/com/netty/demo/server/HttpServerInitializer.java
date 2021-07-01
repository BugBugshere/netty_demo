package com.netty.demo.server;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //处理逻辑
        //1. 处理http消息的编解码
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        //2. 添加自定义handler
        //pipeline.addLast("httpServerHandler", new HttpServerChannelHandler0());
        //模拟重复添加一次
        //pipeline.addLast("httpServerHandler2", new HttpServerChannelHandler0());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        //添加自定义的ChannelHandler
        pipeline.addLast("httpServerHandler3", new HttpServerChannelHandler1());
    }
}
