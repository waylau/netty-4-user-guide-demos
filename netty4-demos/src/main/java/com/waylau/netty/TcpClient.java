package com.waylau.netty;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * TCP Client.
 * 
 * @since 1.0.0 2019年12月12日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class TcpClient {

	public static void main(String[] args) throws IOException  {
		Socket socket = null;  
        OutputStream out = null;  
  
        try {  
  
            socket = new Socket("localhost", 8023);  
            out = socket.getOutputStream();  
  
            // 请求服务器  
            String lines = "床前明月光\r\n疑是地上霜\r\n举头望明月\r\n低头思故乡\r\n";  
            byte[] outputBytes = lines.getBytes("UTF-8");  
            out.write(outputBytes);  
            out.flush();  
  
        } finally {  
            // 关闭连接  
            out.close();  
            socket.close();  
        }  

	}

}
