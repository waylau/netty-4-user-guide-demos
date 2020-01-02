package com.waylau.netty.demo.codec.jackcon;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * JacksonClient Handler.
 * 
 * @since 1.0.0 2020年1月2日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class JacksonClientHandler extends
		SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object obj)
			throws Exception {
		String jsonString = "";
		if (obj instanceof JacksonBean) {
			JacksonBean user = (JacksonBean) obj;
			jsonString = JacksonMapper.getInstance().writeValueAsString(user); // 对象转为json字符串
			System.out.println("Server -> Client: " + jsonString);
		}
	}

}
