package com.waylau.netty.demo.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;


/**
 * HeartbeatServer Handler.
 * 
 * @since 1.0.0 2019年12月19日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {
	
	// （1）心跳内容
	private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
			.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
					CharsetUtil.UTF_8));  

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {

		// （2）判断超时类型
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			String type = "";
			if (event.state() == IdleState.READER_IDLE) {
				type = "read idle";
			} else if (event.state() == IdleState.WRITER_IDLE) {
				type = "write idle";
			} else if (event.state() == IdleState.ALL_IDLE) {
				type = "all idle";
			}

			// （3）发送心跳
			ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(
					ChannelFutureListener.CLOSE_ON_FAILURE);
 
			System.out.println( ctx.channel().remoteAddress()+"超时类型：" + type);
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}
}
