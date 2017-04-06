package com.waylau.netty.demo.codec.serialization;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * 说明：序列化服务器初始化
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月6日
 */
public class SerializationServerHandlerInitializer extends
		ChannelInitializer<Channel> {

	private final static int MAX_OBJECT_SIZE = 1024 * 1024;

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new ObjectDecoder(MAX_OBJECT_SIZE,
				ClassResolvers.weakCachingConcurrentResolver(this.getClass()
						.getClassLoader())));
		pipeline.addLast(new ObjectEncoder());
		pipeline.addLast(new SerializationServerHandler());
	}
}