/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.waylau.netty.demo.file;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * 说明：文件服务器处理器
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日
 */
public class FileServerHandler extends SimpleChannelInboundHandler<String> {

    private static final String CR = System.getProperty("line.separator");
 
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		File file = new File(msg);
		if (file.exists()) {
		    if (!file.isFile()) {
			ctx.writeAndFlush("Not a file : " + file + CR);
			return;
		    }
		    ctx.write(file + " " + file.length() + CR);
		    RandomAccessFile randomAccessFile = new RandomAccessFile(msg, "r");
		    FileRegion region = new DefaultFileRegion(
			    randomAccessFile.getChannel(), 0, randomAccessFile.length());
		    ctx.write(region);
		    ctx.writeAndFlush(CR);
		    randomAccessFile.close();
		} else {
		    ctx.writeAndFlush("File not found: " + file + CR);
		}
		
	}
}
