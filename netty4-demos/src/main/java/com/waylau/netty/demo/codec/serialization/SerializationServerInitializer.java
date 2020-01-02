package com.waylau.netty.demo.codec.serialization;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * SerializationServer ChannelInitializer.
 * 
 * @since 1.0.0 2020年1月2日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class SerializationServerInitializer extends ChannelInitializer<Channel> {

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