/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.waylau.netty.demo.file;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 说明：文件服务器
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日
 */
public class FileServer {

    public void run(int port) throws Exception {
	EventLoopGroup bossGroup = new NioEventLoopGroup();
	EventLoopGroup workerGroup = new NioEventLoopGroup();
	try {
	    ServerBootstrap b = new ServerBootstrap();
	    b.group(bossGroup, workerGroup)
		    .channel(NioServerSocketChannel.class)
		    .option(ChannelOption.SO_BACKLOG, 100)
		    .childHandler(new ChannelInitializer<SocketChannel>() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * io.netty.channel.ChannelInitializer#initChannel(io
				 * .netty.channel.Channel)
				 */
				public void initChannel(SocketChannel ch)
					throws Exception {
				    ch.pipeline().addLast(
					    new StringEncoder(CharsetUtil.UTF_8),
					    new LineBasedFrameDecoder(1024),
					    new StringDecoder(CharsetUtil.UTF_8),
					    new FileServerHandler());
				}
		    });
	    ChannelFuture f = b.bind(port).sync();
	    System.out.println("Server start at port : " + port);
	    f.channel().closeFuture().sync();
	} finally {
	    // 优雅停机
	    bossGroup.shutdownGracefully();
	    workerGroup.shutdownGracefully();
	}
    }

    public static void main(String[] args) throws Exception {
	int port = 8082;
	if (args.length > 0) {
	    try {
		port = Integer.parseInt(args[0]);
	    } catch (NumberFormatException e) {
		e.printStackTrace();
	    }
	}
	new FileServer().run(port);
    }
}
