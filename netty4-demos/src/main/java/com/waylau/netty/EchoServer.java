package com.waylau.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

	private final int port;
	 
    public EchoServer( int port )
    {
        this.port = port;
    }
 
    public void start() throws Exception
    {
 
        EventLoopGroup group = new NioEventLoopGroup();
        try
        {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group( group )//
                    .channel( NioServerSocketChannel.class )//
                    .localAddress(port)//
                    .childHandler(
                            new ChannelInitializer<SocketChannel>()
                            {
                                @Override
                                protected void initChannel( SocketChannel ch ) throws Exception
                                {
                                    ch.pipeline().addLast( new EchoServerHandler() );
                                }
                            } //
                    );
            ChannelFuture f = bootstrap.bind().sync();
            System.out.println(
                    EchoServer.class.getName()
                    + " started and listen on " + f.channel().localAddress()
            );
 
            f.channel().closeFuture().sync();
        }
        finally
        {
            group.shutdownGracefully().sync();
        }
    }
    
	public static void main(String[] args) throws Exception {
         new EchoServer( 65535 ).start();
	}

}
