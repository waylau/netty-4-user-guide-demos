package com.waylau.java.demo.reactor.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Handler
 * 
 * @since 1.0.0 2019年11月14日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class Handler implements Runnable {

	private final SelectionKey selectionKey;
	private final SocketChannel socketChannel;

	private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	private ByteBuffer sendBuffer = ByteBuffer.allocate(2048);

	private final static int READ = 0;
	private final static int SEND = 1;

	private int status = READ;

	Handler(SocketChannel socketChannel, Selector selector) throws IOException {
		this.socketChannel = socketChannel; // 接收客户端连接
		this.socketChannel.configureBlocking(false); // 置为非阻塞模式
		selectionKey = socketChannel.register(selector, 0); // 将该客户端注册到selector
		selectionKey.attach(this); // 附加处理对象，当前是Handler对象
		selectionKey.interestOps(SelectionKey.OP_READ); // 连接已完成，那么接下来就是读取动作
		selector.wakeup(); // 唤起select阻塞
	}

	@Override
	public void run() {
		try {
			switch (status) {
			case READ:
				read();
				break;
			case SEND:
				send();
				break;
			default:
			}
		} catch (IOException e) {
			// 这里的异常处理是做了汇总，常出的异常就是server端还有未读/写完的客户端消息，
			// 客户端就主动断开连接，这种情况下是不会触发返回-1的，
			// 这样下面read和write方法里的cancel和close就都无法触发，这样会导致死循环异常
			// （read/write处理失败，事件又未被cancel，因此会不断的被select到，不断的报异常）
			System.err.println("read或send时发生异常！异常信息：" + e.getMessage());
			selectionKey.cancel();
			try {
				socketChannel.close();
			} catch (IOException e2) {
				System.err.println("关闭通道时发生异常！异常信息：" + e2.getMessage());
				e2.printStackTrace();
			}
		}
	}

	private void read() throws IOException {
		if (selectionKey.isValid()) {
			System.out.println("服务端读取数据前");
			readBuffer.clear();

			// read方法结束，意味着本次"读就绪"变为"读完毕"，标记着一次就绪事件的结束
			int count = socketChannel.read(readBuffer);
			if (count > 0) {
				try {
					Thread.sleep(5000L); // 读取信息后睡眠5s当做业务处理瓶颈
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(String.format("recived %s from %s",
						new String(readBuffer.array()), socketChannel.getRemoteAddress()));
				status = SEND;
				selectionKey.interestOps(SelectionKey.OP_WRITE); // 注册写方法
			} else {
				// 读模式下拿到的值是-1，说明客户端已经断开连接，那么将对应的selectKey从selector里清除，
				// 否则下次还会select到，因为断开连接意味着读就绪不会变成读完毕，也不cancel，
				// 下次select会不停收到该事件.
				// 所以在这种场景下，（服务器程序）你需要关闭socketChannel并且取消key，最好是退出当前函数。
				// 注意，这个时候服务端要是继续使用该socketChannel进行读操作的话，就会抛出“远程主机强迫关闭一个现有的连接”的IO异常。
				selectionKey.cancel();
				socketChannel.close();
				System.out.println("read close");
			}
		}
	}

	void send() throws IOException {
		if (selectionKey.isValid()) {
			sendBuffer.clear();
			sendBuffer.put(String
					.format("recived %s from %s", new String(readBuffer.array()), socketChannel.getRemoteAddress())
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
			selectionKey.interestOps(SelectionKey.OP_READ);
		}
	}
}
