package com.waylau.netty.demo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * My Decoder.
 * 
 * @since 1.0.0 2019年12月16日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class MyDecoder extends LengthFieldBasedFrameDecoder {
	
	private static final int MAX_FRAME_LENGTH = 1024 * 1024;
	private static final int LENGTH_FIELD_LENGTH = 1;
	private static final int LENGTH_FIELD_OFFSET = 4;
	private static final int LENGTH_ADJUSTMENT = 0;
	private static final int INITIAL_BYTES_TO_STRIP = 0;
	
	private static final int HEADER_SIZE = 5;
	private byte msgType; // 消息类型
	private int len; // 长度

	public MyDecoder() {
		super(MAX_FRAME_LENGTH,
				LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
				LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP);
	}

	@Override
	protected Msg decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
		ByteBuf in = (ByteBuf) super.decode(ctx, in2);
		if (in == null) {
			return null;
		}

		// 校验头长度
		if (in.readableBytes() < HEADER_SIZE) {
			return null;
		}

		msgType = in.readByte();
		len = in.readInt();

		// 校验消息体长度
		if (in.readableBytes() < len) {
			return null;
		}

		ByteBuf buf = in.readBytes(len);
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		
		// ByteBuf转为Msg类型
		Msg msg = new Msg();
		MsgHeader msgHeader = new MsgHeader(msgType, len);
		msg.setBody(body);
		msg.setMsgHeader(msgHeader);
		return msg;
	}
}
