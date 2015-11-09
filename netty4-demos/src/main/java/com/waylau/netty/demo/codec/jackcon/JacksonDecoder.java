package com.waylau.netty.demo.codec.jackcon;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月9日
 */
public class JacksonDecoder extends ByteToMessageDecoder {

	/**
	 * 
	 */
	public JacksonDecoder() {
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
        
        ByteBufInputStream byteBufInputStream = new ByteBufInputStream(in);
        out.add(JacksonMapper.getInstance().readValue(byteBufInputStream, JacksonBean.class));
	}

}
