package com.waylau.netty.demo.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * DatagramChannel Echo Client.
 * 
 * @since 1.0.0 2019年10月22日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public final class DatagramChannelEchoClient {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("用法: java DatagramChannelEchoClient <host name> <port number>");
			System.exit(1);
		}

		String host = args[0];
		int port = Integer.parseInt(args[1]);

		// 配置客户端
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioDatagramChannel.class)
			.option(ChannelOption.SO_BROADCAST, true)
			.handler(new DatagramChannelEchoClientHandler());

			// 绑定端口
			ChannelFuture f = b.bind(port).sync();

			System.out.println("DatagramChannelEchoClient已启动，端口：" + port);
			
			Channel channel = f.channel();
			ByteBuffer writeBuffer = ByteBuffer.allocate(32);
			try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
				String userInput;
				while ((userInput = stdIn.readLine()) != null) {
					writeBuffer.put(userInput.getBytes());
					writeBuffer.flip();
					writeBuffer.rewind();
					
					// 转为ByteBuf
					ByteBuf buf = Unpooled.copiedBuffer(writeBuffer);
					
					// 写消息到管道
					// 消息封装为DatagramPacket类型
					channel.writeAndFlush(new DatagramPacket(buf, 
							new InetSocketAddress(host, DatagramChannelEchoServer.DEFAULT_PORT)));
					
					// 清理缓冲区
					writeBuffer.clear();
				}
			} catch (UnknownHostException e) {
				System.err.println("不明主机，主机名为： " + host);
				System.exit(1);
			} catch (IOException e) {
				System.err.println("不能从主机中获取I/O，主机名为：" + host);
				System.exit(1);
			}
		} finally {

			// 优雅的关闭
			group.shutdownGracefully();
		}
	}
}
