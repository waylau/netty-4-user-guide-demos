/**
 * 
 */
package com.waylau.netty.demo.protocol;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月10日
 */
public class ProtocolDecoderDeprecation extends ByteToMessageDecoder {

	private static final int HEADER_SIZE = 10;

	private byte magic; // 魔数
	private byte msgType; // 消息类型
	private short reserve; // 保留字
	private short sn; // 序列号
	private int len; // 长度

	/**
	 * 
	 */
	public ProtocolDecoderDeprecation() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.
	 * ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		if (in.readableBytes() < HEADER_SIZE) {
			return;// response header is 10 bytes
		}

		magic = in.readByte();
		msgType = in.readByte();
		reserve = in.readShort();
		sn = in.readShort();
		len = in.readInt();

		if (in.readableBytes() < len) {
			return; // until we have the entire payload return
		}

		ByteBuf buf = in.readBytes(len);
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		ProtocolMsg msg = new ProtocolMsg();

//		ProtocolBody body2 = new ProtocolBody();
//		body2.setBody(body);
		ProtocolHeader protocolHeader = new ProtocolHeader(magic, msgType,
				reserve, sn, len);
		//msg.setProtocolBody(body2);
		msg.setBody(body);
		msg.setProtocolHeader(protocolHeader);
		out.add(msg);

	}

}
