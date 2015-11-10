package com.waylau.netty.demo.codec.jackcon;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 说明：处理器
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月7日 
 */
public class JacksonServerHandler extends SimpleChannelInboundHandler<Object> {
 

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object obj)
			throws Exception {
		String jsonString = "";
		if (obj instanceof JacksonBean) {
			JacksonBean user = (JacksonBean)obj;
			
			ctx.writeAndFlush(user);

			jsonString = JacksonMapper.getInstance().writeValueAsString(user); // 对象转为json字符串

		} else {
			ctx.writeAndFlush(obj);
			jsonString = JacksonMapper.getInstance().writeValueAsString(obj); // 对象转为json字符串
		}
		
		System.out.println("Server get msg form Client -" + jsonString);
	}
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
    	Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
