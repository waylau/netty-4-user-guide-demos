package com.waylau.netty.demo.codec.jackcon;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * 说明：处理器初始化
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月6日
 */
public class JacksonClientHandlerInitializer extends
		ChannelInitializer<Channel> {
 

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new JacksonDecoder<JacksonBean>(JacksonBean.class));
		pipeline.addLast(new JacksonEncoder());
		pipeline.addLast(new JacksonClientHandler());
	}
}