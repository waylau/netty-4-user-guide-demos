/**
 * Welcome to https://waylau.com
 */
package com.waylau.netty.demo.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * My LineBasedFrameDecoder ServerHandler。
 * 
 * @since 1.0.0 2019年12月12日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class MyLineBasedFrameDecoderServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// 接收msg消息，此处已经无需解码了
		System.out.println("Client -> Server: " + msg);
	}
}
