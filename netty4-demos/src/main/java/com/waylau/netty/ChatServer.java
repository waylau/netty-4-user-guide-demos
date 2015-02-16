package com.waylau.netty;
 
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatServer {
	private final int port;

	public ChatServer(int port) {
		this.port = port;
	}
	public void run() throws InterruptedException {
		System.out.println("============server running ============");
		EventLoopGroup bossGroup = new NioEventLoopGroup();  
		EventLoopGroup workerGroup = new NioEventLoopGroup(); //1.

		try {
			ServerBootstrap b = new ServerBootstrap(); //2.    
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class) 
			.childHandler(new ChatServerInitializer());  //3 

			ChannelFuture cf = b.bind(port).syncUninterruptibly(); //4
			cf.channel().closeFuture().sync();//  use this line to close the channel 
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();//5  
		}
	}
   public static void main(String[] args) throws Exception {
		new ChatServer(8000).run(); // don't hardcode like this. 
   }
}