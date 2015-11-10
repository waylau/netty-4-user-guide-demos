/**
 * 
 */
package com.waylau.netty.demo.protocol;

import com.waylau.netty.util.ByteObjConverter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月10日 
 */
public class ProtocolEncoder extends MessageToByteEncoder<ProtocolMsg> {

	/**
	 * 
	 */
	public ProtocolEncoder() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param outboundMessageType
	 */
	public ProtocolEncoder(Class<? extends ProtocolMsg> outboundMessageType) {
		super(outboundMessageType);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param preferDirect
	 */
	public ProtocolEncoder(boolean preferDirect) {
		super(preferDirect);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param outboundMessageType
	 * @param preferDirect
	 */
	public ProtocolEncoder(Class<? extends ProtocolMsg> outboundMessageType,
			boolean preferDirect) {
		super(outboundMessageType, preferDirect);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.buffer.ByteBuf)
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, ProtocolMsg msg,
			ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		ProtocolHeader header = msg.getProtocolHeader();
		ProtocolBody body = msg.getProtocolBody();
		byte[] bodyBytes = ByteObjConverter.ObjectToByte(body);
		int bodySize = bodyBytes.length;
		
		out.writeByte(header.getMagic());
		out.writeByte(header.getMsgType());
		out.writeShort(header.getReserve());
		out.writeShort(header.getSn());
		out.writeShort(bodySize);
		out.writeBytes(bodyBytes);
	}

}
