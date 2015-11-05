/**
 * 
 */
package com.waylau.netty.demo.protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;






import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日 
 */
public class ProtocolServerHandler implements ChannelInboundHandler {

	private static Map<Integer, Channel> channels = new ConcurrentHashMap<Integer, Channel>();
	private static int uuid = 0;
	private static int getUUID() {
		return uuid ++;
	}
	/**
	 * 
	 */
	public ProtocolServerHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handlerAdded(ChannelHandlerContext channelhandlercontext)
			throws Exception {
        Channel incoming = channelhandlercontext.channel();
        int channelId = getUUID();
        
        // 广播给其他所有管道
        for (Channel channel : channels.values()) {  
        	 channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + ";UUID:"+channelId+" 加入");
        }
        channels.put(channelId, incoming);
		System.out.println("Client:"+incoming.remoteAddress() +";UUID:"+channelId+" 加入");
		
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext channelhandlercontext)
			throws Exception {
        Channel incoming = channelhandlercontext.channel();
        int channelId = 0;
        for (Map.Entry<Integer, Channel> m :channels.entrySet()) {  
        	if(m.getValue().equals(incoming)){
        		channelId = m.getKey();
        	}
        }  
        
        // 广播给其他所有管道
        if (channelId > 0) {
    		System.out.println("Client:"+incoming.remoteAddress() +"离开");
            channels.remove(channelId);
            
            for (Channel channel : channels.values()) {  
                channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + ";UUID:"+channelId+" 离开");
            }
        }
		
	}

	@Override
	public void channelRegistered(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void channelActive(ChannelHandlerContext channelhandlercontext)
			throws Exception {
        Channel incoming = channelhandlercontext.channel();
		System.out.println("Client:"+incoming.remoteAddress()+"在线");
		
	}

	@Override
	public void channelInactive(ChannelHandlerContext channelhandlercontext)
			throws Exception {
        Channel incoming = channelhandlercontext.channel();
		System.out.println("Client:"+incoming.remoteAddress()+"离线");
	}

	@Override
	public void channelRead(ChannelHandlerContext channelhandlercontext,
			Object obj) throws Exception {
        Channel incoming = channelhandlercontext.channel();
		System.out.println("Client->Server:"+incoming.remoteAddress()+obj.toString());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext channelhandlercontext,
			Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void channelWritabilityChanged(
			ChannelHandlerContext channelhandlercontext) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext channelhandlercontext,
			Throwable throwable) throws Exception {
		// TODO Auto-generated method stub
		
	}



}
