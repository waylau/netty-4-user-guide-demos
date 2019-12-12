/**
 * Welcome to https://waylau.com
 */
package com.waylau.netty.demo.decoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * My LineBasedFrameDecoder ChannelInitializer。
 * 
 * @since 1.0.0 2019年12月12日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class MyLineBasedFrameDecoderChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel channel) {
		// 基于换行符号
		channel.pipeline().addLast(new MyLineBasedFrameDecoder());

		// 解码转String
		channel.pipeline().addLast(new StringDecoder());

		// 自定义ChannelHandler
		channel.pipeline().addLast(new MyLineBasedFrameDecoderServerHandler());
	}
}
