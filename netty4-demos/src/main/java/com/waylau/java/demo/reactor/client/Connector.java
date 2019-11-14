package com.waylau.java.demo.reactor.client;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Connector
 * 
 * @since 1.0.0 2019年11月14日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class Connector implements Runnable {

	private final Selector selector;

	private final SocketChannel socketChannel;

	Connector(SocketChannel socketChannel, Selector selector) {
		this.socketChannel = socketChannel;
		this.selector = selector;
	}

	@Override
	public void run() {
		try {
			if (socketChannel.finishConnect()) {
				// 这里连接完成（与服务端的三次握手完成）
				System.out.println(String.format("connected to %s", socketChannel.getRemoteAddress()));

				// 连接建立完成后，接下来的动作交给Handler去处理（读写等）
				new Handler(socketChannel, selector);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
