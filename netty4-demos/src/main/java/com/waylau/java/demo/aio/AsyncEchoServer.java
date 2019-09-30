/**
 * Welcome to https://waylau.com
 */
package com.waylau.java.demo.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Async Echo Server.
 * 
 * @since 1.0.0 2019年9月29日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class AsyncEchoServer {
	public static int DEFAULT_PORT = 7;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port;

		try {
			port = Integer.parseInt(args[0]);
		} catch (RuntimeException ex) {
			port = DEFAULT_PORT;
		}

		AsynchronousServerSocketChannel serverChannel;
		try {
			serverChannel = AsynchronousServerSocketChannel.open();
			InetSocketAddress address = new InetSocketAddress(port);
			serverChannel.bind(address);

			// 设置阐述
			serverChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
			serverChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

			System.out.println("AsyncEchoServer已启动，端口：" + port);
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}

		while (true) {

			// 可连接
			Future<AsynchronousSocketChannel> future = serverChannel.accept();
			AsynchronousSocketChannel socketChannel = null;
			try {
				socketChannel = future.get();
			} catch (InterruptedException | ExecutionException e) {
				System.out.println("AsyncEchoServer异常!" + e.getMessage());
			}

			System.out.println("AsyncEchoServer接受客户端的连接：" + socketChannel);

			// 分配缓存区
			ByteBuffer buffer = ByteBuffer.allocate(100);

			try {
				while (socketChannel.read(buffer).get() != -1) {
					buffer.flip();
					socketChannel.write(buffer).get();
					

					System.out.println("AsyncEchoServer  -> " 
							+ socketChannel.getRemoteAddress() + "：" + buffer.toString());
					
					
					if (buffer.hasRemaining()) {
						buffer.compact();
					} else {
						buffer.clear();
					}
				}

				socketChannel.close();
			} catch (InterruptedException | ExecutionException | IOException e) {
				System.out.println("AsyncEchoServer异常!" + e.getMessage());

			}

		}

	}
}
