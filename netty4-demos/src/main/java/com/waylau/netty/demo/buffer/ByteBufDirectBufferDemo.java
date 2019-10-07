/**
 * Welcome to https://waylau.com
 */
package com.waylau.netty.demo.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * ByteBuf with Direct Buffer Mode Demo.
 * 
 * @since 1.0.0 2019年10月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class ByteBufDirectBufferDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// 创建一个直接缓冲区
		ByteBuf buffer = Unpooled.directBuffer(10);
		String s = "waylau";
		buffer.writeBytes(s.getBytes());

		// 检查是否是支撑数组.
		// 不是支撑数组，则为直接缓冲区
		if (!buffer.hasArray()) {

			// 计算第一个字节的偏移量
			int offset = buffer.readerIndex();

			// 可读字节数
			int length = buffer.readableBytes();

			// 获取字节内容
			byte[] array = new byte[length];
			buffer.getBytes(offset, array);

			printBuffer(array, offset, length);
		}
	}

	/**
	 * 打印出Buffer的信息
	 * 
	 * @param buffer
	 */
	private static void printBuffer(byte[] array, int offset, int len) {
		System.out.println("array：" + array);
		System.out.println("array->String：" + new String(array));
		System.out.println("offset：" + offset);
		System.out.println("len：" + len);
	}
}
