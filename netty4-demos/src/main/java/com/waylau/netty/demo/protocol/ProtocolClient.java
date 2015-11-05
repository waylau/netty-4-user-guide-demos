/**
 * 
 */
package com.waylau.netty.demo.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 说明：自定义协议客户端
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日 
 */
public class ProtocolClient {

	private String host;
	private int port;
	
	/**
	 * 
	 */
	public ProtocolClient(String host,int port) {
		this.host = host;
		this.port = port;
	}
	
	public void run() throws InterruptedException {
		
		 EventLoopGroup workerGroup = new NioEventLoopGroup();

	     try {
	         Bootstrap b = new Bootstrap(); // (1)
	         b.group(workerGroup); // (2)
	         b.channel(NioSocketChannel.class); // (3)
	         b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
	         b.handler(new ChannelInitializer<SocketChannel>() {
	             @Override
	             public void initChannel(SocketChannel ch) throws Exception {
	                 ch.pipeline().addLast(new ProtocolClientHandler());
	             }
	         });

	         // 启动客户端
	         ChannelFuture f = b.connect(host, port).sync(); // (5)

	         // 等待连接关闭
	         f.channel().closeFuture().sync();
	     } finally {
	         workerGroup.shutdownGracefully();
	     }
	}
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
 		new ProtocolClient("localhost",8082).run();
	}

}
