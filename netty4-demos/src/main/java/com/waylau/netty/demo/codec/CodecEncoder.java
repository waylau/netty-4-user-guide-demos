/**
 * 
 */
package com.waylau.netty.demo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 说明：编码器
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月7日 
 */
public class CodecEncoder extends MessageToByteEncoder<MessageBody> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageBody obj, ByteBuf out)
			throws Exception {
 
//        int dataLength = obj.length;  //读取消息的长度
//        out.writeInt(dataLength);  //先将消息长度写入，也就是消息头
//        out.writeBytes(obj.);  //消息体中包含我们要发送的数据
		
	}

}
