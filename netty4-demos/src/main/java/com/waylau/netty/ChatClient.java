package com.waylau.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;



import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * Simple  chat client 
 */
public class ChatClient {
	private final String host;
	private final int port;

	public ChatClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	public void run() throws Exception {    	
		EventLoopGroup group = new NioEventLoopGroup(); // 1
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChatClientInitializer()); //2
			
			Channel ch; 
			// Start the connection attempt.
			ch = b.connect(host, port).sync().channel(); //3

			ChannelFuture lastWriteFuture;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				String line = in.readLine();
				lastWriteFuture = ch.writeAndFlush(line+"\n"); 
				if (line == null) {
					break;
				}    // Sends the received line to the server.
				lastWriteFuture.addListener(new ChannelFutureListener() { // adding a listener to 
					public void operationComplete(ChannelFuture future) {
					Channel ch = future.channel();
						//ch.close();  // closing the channel once its done. 
					}					
	            });
				//lastWriteFuture.channel().closeFuture().sync(); // 
			}
		}
		finally{
			group.shutdownGracefully();
		}
 }
	public static void main(String[] args) throws Exception {
		new ChatClient("localhost", 8000).run(); // there are better ways to do it. 
	}
}
