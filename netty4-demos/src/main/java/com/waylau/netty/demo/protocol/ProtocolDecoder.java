package com.waylau.netty.demo.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月11日 
 */
public class ProtocolDecoder extends LengthFieldBasedFrameDecoder {
	private static final int HEADER_SIZE = 10;

	private byte magic; // 魔数
	private byte msgType; // 消息类型
	private short reserve; // 保留字
	private short sn; // 序列号
	private int len; // 长度
 
	/**
	 * @param maxFrameLength
	 * @param lengthFieldOffset
	 * @param lengthFieldLength
	 * @param lengthAdjustment
	 * @param initialBytesToStrip
	 */
	public ProtocolDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
				lengthAdjustment, initialBytesToStrip);
 	}

	/**
	 * @param maxFrameLength
	 * @param lengthFieldOffset
	 * @param lengthFieldLength
	 * @param lengthAdjustment
	 * @param initialBytesToStrip
	 * @param failFast
	 */
	public ProtocolDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength, int lengthAdjustment,
			int initialBytesToStrip, boolean failFast) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
				lengthAdjustment, initialBytesToStrip, failFast);
		// TODO Auto-generated constructor stub
	}

 
	@Override
	protected ProtocolMsg decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
		ByteBuf in = (ByteBuf) super.decode(ctx, in2);
		if (in == null) {
		    return null;
		}
		
		if (in.readableBytes() < HEADER_SIZE) {
			return null;// response header is 10 bytes
		}

		magic = in.readByte();
		msgType = in.readByte();
		reserve = in.readShort();
		sn = in.readShort();
		len = in.readInt();

		if (in.readableBytes() < len) {
			return null; // until we have the entire payload return
		}

		ByteBuf buf = in.readBytes(len);
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		ProtocolMsg msg = new ProtocolMsg();
		ProtocolHeader protocolHeader = new ProtocolHeader(magic, msgType,
				reserve, sn, len);
		msg.setBody(body);
		msg.setProtocolHeader(protocolHeader);
		return msg;
	}
}
