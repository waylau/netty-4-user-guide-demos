package com.waylau.netty.demo.factorial;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Handler for a client-side channel.  This handler maintains stateful
 * information which is specific to a certain channel using member variables.
 * Therefore, an instance of this handler can cover only one channel.  You have
 * to create a new handler instance whenever you create a new channel and insert
 * this handler to avoid a race condition.
 */
public class FactorialClientHandler extends SimpleChannelInboundHandler<BigInteger> {

    private ChannelHandlerContext ctx;
    private int receivedMessages;
    private int next = 1;
    final BlockingQueue<BigInteger> answer = new LinkedBlockingQueue<BigInteger>();

    public BigInteger getFactorial() {
        boolean interrupted = false;
        try {
            for (;;) {
                try {
                    return answer.take();
                } catch (InterruptedException ignore) {
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        sendNumbers();
    }

    public void messageReceived(ChannelHandlerContext ctx, final BigInteger msg) {
        receivedMessages ++;
        if (receivedMessages == FactorialClient.COUNT) {
            // Offer the answer after closing the connection.
            ctx.channel().close().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    boolean offered = answer.offer(msg);
                    assert offered;
                }
            });
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private void sendNumbers() {
        // Do not send more than 4096 numbers.
        ChannelFuture future = null;
        for (int i = 0; i < 4096 && next <= FactorialClient.COUNT; i++) {
            future = ctx.write(Integer.valueOf(next));
            next++;
        }
        if (next <= FactorialClient.COUNT) {
            assert future != null;
            future.addListener(numberSender);
        }
        ctx.flush();
    }

    private final ChannelFutureListener numberSender = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
                sendNumbers();
            } else {
                future.cause().printStackTrace();
                future.channel().close();
            }
        }
    };

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, BigInteger arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
