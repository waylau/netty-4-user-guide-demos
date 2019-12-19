package com.waylau.netty.demo.heartbeat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Heartbeat Handler Initializer
 * 
 * @since 1.0.0 2019年12月19日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class HeartbeatHandlerInitializer extends ChannelInitializer<Channel> {

	private static final int READ_IDEL_TIME_OUT = 4; // 读超时
	private static final int WRITE_IDEL_TIME_OUT = 5;// 写超时
	private static final int ALL_IDEL_TIME_OUT = 7; // 所有超时

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT,
				WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS)); // （1）
		pipeline.addLast(new HeartbeatServerHandler()); // （2）
	}
}