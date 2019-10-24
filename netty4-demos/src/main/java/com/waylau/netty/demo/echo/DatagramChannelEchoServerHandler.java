package com.waylau.netty.demo.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;

/**
 * DatagramChannel Echo Server Handler.
 * 
 * @since 1.0.0 2019年10月24日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class DatagramChannelEchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// 消息转为DatagramPacket类型
		DatagramPacket packet = (DatagramPacket)msg;
		
		System.out.println(packet.sender() + " -> Server :" + msg);
		
		// 构建新DatagramPacket
		DatagramPacket data = new DatagramPacket(packet.content(), packet.sender());
		
		// 写消息到管道
		ctx.write(data);// 写消息
		ctx.flush(); // 刷新消息
		
		// 上面两个方法等同于 ctx.writeAndFlush(data);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}