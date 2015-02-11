package com.waylau.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>
{
 
    @Override
    public void channelActive( ChannelHandlerContext ctx ) throws Exception
    {
        ctx.writeAndFlush( Unpooled.copiedBuffer( "Netty rock!", CharsetUtil.UTF_8 ) );
    }
 
    @Override
    protected void channelRead0( ChannelHandlerContext ctx, ByteBuf in ) throws Exception
    {
        System.out.println( "Client received : "
                            + ByteBufUtil.hexDump( in.readBytes( in.readableBytes() ) ) );

    }
 
    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
 
}