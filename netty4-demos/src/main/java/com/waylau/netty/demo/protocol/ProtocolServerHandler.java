package com.waylau.netty.demo.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 说明：处理器
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月7日 
 */
public class ProtocolServerHandler extends SimpleChannelInboundHandler<Object> {
 

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object obj)
			throws Exception {
        Channel incoming = ctx.channel();
		
		if(obj instanceof ProtocolMsg) {
			ProtocolMsg msg = (ProtocolMsg)obj;
			System.out.println("Client->Server:"+incoming.remoteAddress()+msg.getBody());
			incoming.write(obj);
		}
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
