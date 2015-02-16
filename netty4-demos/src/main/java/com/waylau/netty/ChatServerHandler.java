package com.waylau.netty;

 
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles a server-side channel.
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
	
	   
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
    	//invoked as many times as your client connects 
    	    }
    
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
   
        ctx.close();
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {		
			 
			System.out.println("message recieved is: "+msg.toString());
			
			}

	
}