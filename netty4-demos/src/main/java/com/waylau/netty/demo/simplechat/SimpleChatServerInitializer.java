package com.waylau.netty.demo.simplechat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class SimpleChatServerInitializer extends
		ChannelInitializer<SocketChannel> {

	@Override
    public void initChannel(SocketChannel ch) throws Exception {

		
		ch.pipeline().addLast(new SimpleChatServerHandler());
		
		System.out.println("SimpleChatClient:" +"连接上");
    }
}
