/**
 * Welcome to https://waylau.com
 */
package com.waylau.java.demo.buffer;

import java.nio.ByteBuffer;

/**
 * ByteBuffer Demo.
 * 
 * @since 1.0.0 2019年10月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class ByteBufferDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 创建一个缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(10);
		System.out.println("------------初始时缓冲区------------");
		printBuffer(buffer);

		// 添加一些数据到缓冲区中
		System.out.println("------------添加数据到缓冲区------------");

		String s = "love";
		buffer.put(s.getBytes());
		printBuffer(buffer);

		// 切换成读模式
		System.out.println("------------执行flip切换到读取模式------------");
		buffer.flip();
		printBuffer(buffer);

		// 读取数据
		System.out.println("------------读取数据------------");

		// 创建一个limit()大小的字节数组(因为就只有limit这么多个数据可读)
		byte[] bytes = new byte[buffer.limit()];

		// 将读取的数据装进我们的字节数组中
		buffer.get(bytes);
		printBuffer(buffer);

		// 执行compact
		System.out.println("------------执行compact------------");
		buffer.compact();
		printBuffer(buffer);

		// 执行clear
		System.out.println("------------执行clear清空缓冲区------------");
		buffer.clear();
		printBuffer(buffer);

	}

	/**
	 * 打印出ByteBuffer的信息
	 * 
	 * @param buffer
	 */
	private static void printBuffer(ByteBuffer buffer) {
		System.out.println("mark：" + buffer.mark());
		System.out.println("position：" + buffer.position());
		System.out.println("limit：" + buffer.limit());
		System.out.println("capacity：" + buffer.capacity());
	}
}
