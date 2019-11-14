package com.waylau.java.demo.reactor.mainsub;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Reactor
 * 
 * @since 1.0.0 2019年11月14日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class Reactor implements Runnable {

	private final Selector selector;
	private final ServerSocketChannel serverSocketChannel;

	public Reactor(int port) throws IOException {
		selector = Selector.open(); // 打开一个Selector
		serverSocketChannel = ServerSocketChannel.open(); // 建立一个Server端通道
		serverSocketChannel.socket().bind(new InetSocketAddress(port)); // 绑定服务端口
		serverSocketChannel.configureBlocking(false); // selector模式下，所有通道必须是非阻塞的

		// Reactor是入口，最初给一个channel注册上去的事件都是accept
		SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		// 绑定Acceptor处理类
		sk.attach(new Acceptor(serverSocketChannel));
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				int count = selector.select(); // 就绪事件到达之前，阻塞
				if (count == 0) {
					continue;
				}
				Set<SelectionKey> selected = selector.selectedKeys(); // 拿到本次select获取的就绪事件
				Iterator<SelectionKey> it = selected.iterator();
				while (it.hasNext()) {
					// 这里进行任务分发
					dispatch((SelectionKey) (it.next()));
				}
				selected.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void dispatch(SelectionKey k) {
		// 附带对象为Acceptor
		Runnable r = (Runnable) (k.attachment());

		// 调用之前注册的回调对象
		if (r != null) {
			r.run();
		}
	}
}
