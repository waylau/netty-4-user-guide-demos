/**
 * 
 */
package com.waylau.netty.demo.codec;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * My Encoder.
 * 
 * @since 1.0.0 2019年12月16日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class MyEncoder extends MessageToByteEncoder<Msg> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf out) throws Exception {
		if (msg == null | msg.getMsgHeader() == null) {
			throw new Exception("The encode message is null");
		}

		// 获取消息头
		MsgHeader header = msg.getMsgHeader();

		// 获取消息体
		String body = msg.getBody();
		byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));

		// 计算消息体的长度
		int bodySize = bodyBytes.length;

		System.out.printf("MyEncoder header: %s, body: %s", header.getMsgType(), body);

		out.writeByte(MsgType.EMGW_LOGIN_RES.getValue());
		out.writeInt(bodySize);
		out.writeBytes(bodyBytes);
	}

}