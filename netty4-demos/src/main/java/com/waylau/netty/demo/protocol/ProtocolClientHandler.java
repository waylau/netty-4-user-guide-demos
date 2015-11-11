package com.waylau.netty.demo.protocol;

import java.nio.charset.Charset;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 说明：处理器
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月7日 
 */
public class ProtocolClientHandler extends SimpleChannelInboundHandler<Object> {
 

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object obj)
			throws Exception {
        Channel incoming = ctx.channel();
		System.out.println("Server->Client:"+incoming.remoteAddress()+obj.toString());
		
		if(obj instanceof ProtocolMsg) {
			ProtocolMsg msg = (ProtocolMsg)obj;
			System.out.println("Server->Client:"+incoming.remoteAddress()+msg.getBody());
		}
	}

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
		// 发送消息给服务器
		ProtocolMsg msg = new ProtocolMsg();
		ProtocolHeader protocolHeader = new ProtocolHeader();
		protocolHeader.setMagic((byte) 0x01);
		protocolHeader.setMsgType((byte) 0x01);
		protocolHeader.setReserve((short) 0);
		protocolHeader.setSn((short) 0);
		String body = "床前明月光疑是地上霜";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 170; i++) {
			sb.append(body);
		}

		byte[] bodyBytes = sb.toString().getBytes(Charset.forName("utf-8"));
		int bodySize = bodyBytes.length;
		protocolHeader.setLen(bodySize);

		msg.setProtocolHeader(protocolHeader);
		msg.setBody(sb.toString());
		
		while (true) {
			ctx.writeAndFlush(msg);
			Thread.sleep(1000);
		}
    }

}
