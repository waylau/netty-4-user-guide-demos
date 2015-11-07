/**
 * 
 */
package com.waylau.netty.demo.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 说明：文件客户端
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月6日
 */
public class FileClient {

	private String host;
	private int port;
	private String dest; // 接收到文件存放的路径
	
	/**
	 * 
	 */
	public FileClient(String host, int port, String dest) {
		this.host = host;
		this.port = port;
		this.dest = dest;
	}

	public void run() throws InterruptedException, IOException {

		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("encoder",
							new StringEncoder(CharsetUtil.UTF_8));
					ch.pipeline().addLast("decoder",
							new StringDecoder(CharsetUtil.UTF_8));
					ch.pipeline().addLast(new FileClientHandler(dest));
				}
			});

			// 启动客户端
			ChannelFuture f = b.connect(host, port).sync(); // (5)
			Channel channel = f.channel();
			
			// 控制台输入请求的文件路径
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			while (true) {
				channel.writeAndFlush(in.readLine() + "\r\n");
			}
			
			// 等待连接关闭
			// f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		new FileClient("localhost", 8082, "D:/reciveFile.txt").run();
	}

}
