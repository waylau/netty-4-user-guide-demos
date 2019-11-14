package com.waylau.java.demo.reactor.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Async Handler
 * 
 * @since 1.0.0 2019年11月14日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class AsyncHandler implements Runnable {

	private final Selector selector;

	private final SelectionKey selectionKey;
	private final SocketChannel socketChannel;

	private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	private ByteBuffer sendBuffer = ByteBuffer.allocate(2048);

	private final static int READ = 0; // 读取就绪
	private final static int SEND = 1; // 响应就绪
	private final static int PROCESSING = 2; // 处理中

	private int status = READ; // 所有连接完成后都是从一个读取动作开始的

	// 开启线程数为4的异步处理线程池
	private static final ExecutorService workers = Executors.newFixedThreadPool(5);

	AsyncHandler(SocketChannel socketChannel, Selector selector) throws IOException {
		this.socketChannel = socketChannel; // 接收客户端连接
		this.socketChannel.configureBlocking(false); // 置为非阻塞模式
		selectionKey = socketChannel.register(selector, 0); // 将该客户端注册到selector
		selectionKey.attach(this); // 附加处理对象，当前是Handler对象
		selectionKey.interestOps(SelectionKey.OP_READ); // 连接已完成，接下来就是读取动作
		this.selector = selector;
		this.selector.wakeup();
	}

	@Override
	public void run() {
		// 如果一个任务正在异步处理，那么这个run是直接不触发任何处理的，
		// read和send只负责简单的数据读取和响应，业务处理完全不阻塞这里的处理
		switch (status) {
		case READ:
			read();
			break;
		case SEND:
			send();
			break;
		default:
		}
	}

	private void read() {
		if (selectionKey.isValid()) {
			try {
				readBuffer.clear();

				// read方法结束，意味着本次"读就绪"变为"读完毕"，标记着一次就绪事件的结束
				int count = socketChannel.read(readBuffer);
				if (count > 0) {
					status = PROCESSING; // 置为处理中
					workers.execute(this::readWorker); // 异步处理
				} else {
					// 读模式下拿到的值是-1，说明客户端已经断开连接，那么将对应的selectKey从selector里清除，
					// 否则下次还会select到，因为断开连接意味着读就绪不会变成读完毕，也不cancel，
					// 下次select会不停收到该事件。
					// 所以在这种场景下，需要关闭socketChannel并且取消key，最好是退出当前函数。
					// 注意，这个时候服务端要是继续使用该socketChannel进行读操作的话，
					// 就会抛出“远程主机强迫关闭一个现有的连接”的IO异常。
					selectionKey.cancel();
					socketChannel.close();
					System.out.println("read closed");
				}
			} catch (IOException e) {
				System.err.println("处理read业务时发生异常！异常信息：" + e.getMessage());
				selectionKey.cancel();
				try {
					socketChannel.close();
				} catch (IOException e1) {
					System.err.println("处理read业务关闭通道时发生异常！异常信息：" + e.getMessage());
				}
			}
		}
	}

	void send() {
		if (selectionKey.isValid()) {
			status = PROCESSING; // 置为执行中
			workers.execute(this::sendWorker); // 异步处理
			selectionKey.interestOps(SelectionKey.OP_READ); // 重新设置为读
		}
	}

	// 读入信息后的业务处理
	private void readWorker() {
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(String.format("client -> Server： %s", new String(readBuffer.array())));
		status = SEND;
		selectionKey.interestOps(SelectionKey.OP_WRITE); // 注册写事件
		this.selector.wakeup(); // 唤醒阻塞在select的线程
	}

	private void sendWorker() {
		try {
			sendBuffer.clear();
			sendBuffer.put(String
					.format("recived %s from %s",  new String(readBuffer.array()),socketChannel.getRemoteAddress())
					.getBytes());
			sendBuffer.flip();

			// write方法结束，意味着本次写就绪变为写完毕，标记着一次事件的结束
			int count = socketChannel.write(sendBuffer);

			if (count < 0) {
				// 同上，write场景下，取到-1，也意味着客户端断开连接
				selectionKey.cancel();
				socketChannel.close();
				System.out.println("send close");
			}

			// 没断开连接，则再次切换到读
			status = READ;
		} catch (IOException e) {
			System.err.println("异步处理send业务时发生异常！异常信息：" + e.getMessage());
			selectionKey.cancel();
			try {
				socketChannel.close();
			} catch (IOException e1) {
				System.err.println("异步处理send业务关闭通道时发生异常！异常信息：" + e.getMessage());
			}
		}
	}
}
