package com.waylau.netty.demo.encoder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * My ClientHandler.
 * 
 * @since 1.0.0 2019年12月16日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object obj) throws Exception {
		Channel incoming = ctx.channel();

		if (obj instanceof Msg) {
			Msg msg = (Msg) obj;
			System.out.println("Server->Client:" + incoming.remoteAddress() + msg.getBody());
		} else {
			System.out.println("Server->Client:" + incoming.remoteAddress() + obj.toString());
		}
	}

}
