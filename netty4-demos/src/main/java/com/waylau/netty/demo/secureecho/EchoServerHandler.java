package com.waylau.netty.demo.secureecho;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Echo Server Handler.
 * 
 * @since 1.0.0 2019年12月25日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// 接收客户端的信息并打印到控制台
		ByteBuf in = (ByteBuf)msg;
		System.out.println(ctx.channel().remoteAddress() + " -> Server :" + 
				in.toString(CharsetUtil.UTF_8));
		
		// 写消息到管道
		ctx.write(msg);// 写消息
		ctx.flush(); // 刷新消息
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}