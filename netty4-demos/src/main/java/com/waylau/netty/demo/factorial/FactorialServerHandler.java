package com.waylau.netty.demo.factorial;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigInteger;

/**
 * Handler for a server-side channel.  This handler maintains stateful
 * information which is specific to a certain channel using member variables.
 * Therefore, an instance of this handler can cover only one channel.  You have
 * to create a new handler instance whenever you create a new channel and insert
 * this handler  to avoid a race condition.
 */
public class FactorialServerHandler extends SimpleChannelInboundHandler<BigInteger> {

    private BigInteger lastMultiplier = new BigInteger("1");
    private BigInteger factorial = new BigInteger("1");

    public void messageReceived(ChannelHandlerContext ctx, BigInteger msg) {
        // Calculate the cumulative factorial and send it to the client.
        lastMultiplier = msg;
        factorial = factorial.multiply(msg);
        ctx.writeAndFlush(factorial);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.printf("Factorial of %,d is: %,d%n", lastMultiplier, factorial);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, BigInteger arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
