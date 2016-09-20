/**
 * 
 */
package com.waylau.netty.demo.file;

import java.io.File;
import java.io.FileOutputStream;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 说明：文件客户端处理器
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月6日
 */
public class FileClientHandler extends SimpleChannelInboundHandler<String> {

	private String dest;

	/**
	 * 
	 * @param dest 文件生成路径
	 */
	public FileClientHandler(String dest) {
		this.dest = dest;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {

		File file = new File(dest);
		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fos = new FileOutputStream(file);

		fos.write(msg.getBytes());
		fos.close();
	}

}
