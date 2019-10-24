/**
 * Welcome to https://waylau.com
 */
package com.waylau.netty.demo.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * DatagramChannel Echo Server.
 * 
 * @since 1.0.0 2019年10月22日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public class DatagramChannelEchoServer {

	public static int DEFAULT_PORT = 7;

	public static void main(String[] args) throws Exception {
		int port;

		try {
			port = Integer.parseInt(args[0]);
		} catch (RuntimeException ex) {
			port = DEFAULT_PORT;
		}

		// 配置事件循环器
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			// 启动NIO服务的引导程序类
			Bootstrap b = new Bootstrap(); 
			
			b.group(group) // 设置EventLoopGroup
			.channel(NioDatagramChannel.class) // 指明新的Channel的类型
			.option(ChannelOption.SO_BROADCAST, true) // 设置的Channel的一些选项
			.handler(new DatagramChannelEchoServerHandler()); // 指定ChannelHandler
			
			// 绑定端口
			ChannelFuture f = b.bind(port).sync(); 

			System.out.println("DatagramChannelEchoServer已启动，端口：" + port);

			// 等待服务器 socket 关闭 。
			// 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
			f.channel().closeFuture().sync();
		} finally {

			// 优雅的关闭
			group.shutdownGracefully();
		}

	}

}
