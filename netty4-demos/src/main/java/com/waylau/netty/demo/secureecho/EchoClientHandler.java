package com.waylau.netty.demo.secureecho;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Echo Client Handler.
 * 
 * @since 1.0.0 2019年10月2日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// 接收服务器的信息并打印到控制台
		ByteBuf in = (ByteBuf) msg;
		System.out.println(ctx.channel().remoteAddress() + " -> Client :" + 
				in.toString(CharsetUtil.UTF_8));

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}