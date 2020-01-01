package com.waylau.netty.demo.websocketchat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * WebSocketChatServer ChannelInitializer.
 * 
 * @since 1.0.0 2020年1月1日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class WebSocketChatServerInitializer extends
		ChannelInitializer<SocketChannel> {	//（1）

	@Override
    public void initChannel(SocketChannel ch) throws Exception {//（2）
		 ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(64*1024));
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new HttpRequestHandler("/ws"));
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		pipeline.addLast(new TextWebSocketFrameHandler());

    }
}
