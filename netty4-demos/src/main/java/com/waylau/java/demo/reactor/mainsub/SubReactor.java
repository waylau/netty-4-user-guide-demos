package com.waylau.java.demo.reactor.mainsub;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * SubReactor
 * 
 * @since 1.0.0 2019年11月14日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class SubReactor implements Runnable {
	private final Selector selector;
	private boolean register = false; // 注册开关表示
	private int num; // 序号，也就是Acceptor初始化SubReactor时的下标

	SubReactor(Selector selector, int num) {
		this.selector = selector;
		this.num = num;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			System.out.println(String.format("NO %d SubReactor waitting for register...", num));
			while (!Thread.interrupted() && !register) {
				try {
					if (selector.select() == 0) {
						continue;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				while (it.hasNext()) {
					dispatch(it.next());
					it.remove();
				}
			}
		}
	}

	private void dispatch(SelectionKey key) {
		Runnable r = (Runnable) (key.attachment());
		if (r != null) {
			r.run();
		}
	}

	void registering(boolean register) {
		this.register = register;
	}

}
