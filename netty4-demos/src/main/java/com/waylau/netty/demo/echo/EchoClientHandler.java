package com.waylau.netty.demo.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		
		// 从管道读消息
		ByteBuf buf = (ByteBuf) msg; // 转为ByteBuf类型
		String m = buf.toString(CharsetUtil.UTF_8);  // 转为字符串
		System.out.println( "echo :" + m);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}