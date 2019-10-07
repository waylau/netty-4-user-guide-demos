/**
 * Welcome to https://waylau.com
 */
package com.waylau.netty.demo.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * ByteBuf Demo.
 * 
 * @since 1.0.0 2019年10月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class ByteBufDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 创建一个缓冲区
		ByteBuf buffer = Unpooled.buffer(10);
		System.out.println("------------初始时缓冲区------------");
		printBuffer(buffer);

		// 添加一些数据到缓冲区中
		System.out.println("------------添加数据到缓冲区------------");

		String s = "love";
		buffer.writeBytes(s.getBytes());
		printBuffer(buffer);

		// 读取数据
		System.out.println("------------读取数据------------");

		while (buffer.isReadable()) {
			System.out.println(buffer.readByte());
		}

		printBuffer(buffer);

		// 执行compact
		System.out.println("------------执行discardReadBytes------------");
		buffer.discardReadBytes();
		printBuffer(buffer);

		// 执行clear
		System.out.println("------------执行clear清空缓冲区------------");
		buffer.clear();
		printBuffer(buffer);

	}

	/**
	 * 打印出ByteBuf的信息
	 * 
	 * @param buffer
	 */
	private static void printBuffer(ByteBuf buffer) {
		System.out.println("readerIndex：" + buffer.readerIndex());
		System.out.println("writerIndex：" + buffer.writerIndex());
		System.out.println("capacity：" + buffer.capacity());
	}
}
