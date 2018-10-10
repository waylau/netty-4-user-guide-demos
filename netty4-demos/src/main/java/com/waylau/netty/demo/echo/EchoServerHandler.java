package com.waylau.netty.demo.echo;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * 处理服务端 channel.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	System.out.println(ctx.channel().remoteAddress()+"->Server :"+ msg.toString());
//        ctx.write(msg); // (1)
//        ctx.flush(); // (2)
//        final ChannelFuture future = ctx.writeAndFlush(msg);
        final ChannelFuture future = ctx.write(msg + "\n");
//        future.addListener(ChannelFutureListener.CLOSE);
//        future.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                System.out.println("close channel");
//                future.channel().close();
//            }
//        });
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server read complete");
        ctx.flush();
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}