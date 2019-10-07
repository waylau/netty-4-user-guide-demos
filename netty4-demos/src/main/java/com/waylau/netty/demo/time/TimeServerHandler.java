package com.waylau.netty.demo.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Time Server Handler.
 * 
 * @since 1.0.0 2019年10月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
	
	@Override
    public void channelActive(final ChannelHandlerContext ctx) {
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        // 出站操作返回ChannelFuture
        final ChannelFuture f = ctx.writeAndFlush(time);
        
        // 增加监听器
        f.addListener(new ChannelFutureListener() {

        	// 操作完成，关闭管道
        	@Override
            public void operationComplete(ChannelFuture future) {
                ctx.close();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
