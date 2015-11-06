
package com.waylau.netty.demo.file;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * 说明：文件服务器处理器
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月6日 
 */
public class FileServerHandler extends SimpleChannelInboundHandler<Object> {

	private File file = new File("D:/2.txt");
	private FileOutputStream fos;

	public FileServerHandler() {
		try {
			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, Object arg1)
			throws Exception {
		ByteBuf buffer = (ByteBuf)arg1 ;
		int length = buffer.readableBytes();
		buffer.readBytes(fos, length);
		fos.flush();
		buffer.clear();
	}


}


