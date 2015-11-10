/**
 * 
 */
package com.waylau.netty.demo.protocol;

import java.nio.charset.Charset;

import com.waylau.netty.util.ByteObjConverter;

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
                     ch.pipeline().addLast("decoder", new ProtocolDecoder());
                     ch.pipeline().addLast("encoder", new ProtocolEncoder());
	                 ch.pipeline().addLast(new ProtocolClientHandler());
	             }
	         });

	         // 启动客户端
	         ChannelFuture f = b.connect(host, port).sync(); // (5)
	         
	         //while (true) {
	        	 ProtocolMsg msg = new ProtocolMsg();
	        	 //ProtocolBody body = new ProtocolBody();
	        	 ProtocolHeader protocolHeader = new ProtocolHeader();
	        	 protocolHeader.setMagic((byte) 0x01);
	        	 protocolHeader.setMsgType((byte) 0x01);
	        	 protocolHeader.setReserve((short)0);
	        	 protocolHeader.setSn((short)0);
	        	 String body = "床前明月光,疑是地上霜,举头望明月,低头思故乡";
	        	 //body.setBody("床前明月光,疑是地上霜,举头望明月,低头思故乡");
	        	 byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
	     		//byte[] bodyBytes = ByteObjConverter.ObjectToByte(body);
	     		int bodySize = bodyBytes.length;
	        	 protocolHeader.setLen(bodySize);
	        	 
	        	 msg.setProtocolHeader(protocolHeader);
	        	 msg.setBody(body);
		         f.channel().writeAndFlush(msg);
		         //Thread.sleep(5000);
	         //}

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
