package com.waylau.netty.demo.httpserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * HTPP Server Handler.
 * 
 * @since 1.0.0 2019年12月26日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		this.readRequest(msg);

		String sendMsg;
		String uri = msg.uri();

		switch (uri) {
		case "/":
			sendMsg = "<h3>Netty HTTP Server</h3><p>Welcome to <a href=\"https://waylau.com\">waylau.com</a>!</p>";
			break;
		case "/hi":
			sendMsg = "<h3>Netty HTTP Server</h3><p>Hello Word!</p>";
			break;
		case "/love":
			sendMsg = "<h3>Netty HTTP Server</h3><p>I Love You!</p>";
			break;
		default:
			sendMsg = "<h3>Netty HTTP Server</h3><p>I was lost!</p>";
			break;
		}

		this.writeResponse(ctx, sendMsg);
	}

	private void readRequest(FullHttpRequest msg) {
		System.out.println("======请求行======");
		System.out.println(msg.method() + " " + msg.uri() + " " + msg.protocolVersion());

		System.out.println("======请求头======");
		for (String name : msg.headers().names()) {
			System.out.println(name + ": " + msg.headers().get(name));

		}

		System.out.println("======消息体======");
		System.out.println(msg.content().toString(CharsetUtil.UTF_8));

	}

	private void writeResponse(ChannelHandlerContext ctx, String msg) {
		ByteBuf bf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);

		FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, bf);
		res.headers().set(HttpHeaderNames.CONTENT_LENGTH, msg.length());
		ctx.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
	}
}