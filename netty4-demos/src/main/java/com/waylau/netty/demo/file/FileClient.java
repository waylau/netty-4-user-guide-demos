/**
 * 
 */
package com.waylau.netty.demo.file;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 说明：自定义协议客户端
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日 
 */
public class FileClient {

	private String host;
	private int port;
	
	/**
	 * 
	 */
	public FileClient(String host,int port) {
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
                	 ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                     ch.pipeline().addLast("decoder", new StringDecoder());
                     ch.pipeline().addLast("encoder", new StringEncoder());
	                 ch.pipeline().addLast(new FileClientHandler());
	             }
	         });

	         // 启动客户端
	         ChannelFuture f = b.connect(host, port).sync(); // (5)
	         
	         while (true) {
		         f.channel().writeAndFlush("床前明月光,疑是地上霜,举头望明月,低头思故乡\r\n");
		         Thread.sleep(500);
	         }

	         // 等待连接关闭
	         //f.channel().closeFuture().sync();
	     } finally {
	         workerGroup.shutdownGracefully();
	     }
	}
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
 		new FileClient("localhost",8082).run();
	}

}
