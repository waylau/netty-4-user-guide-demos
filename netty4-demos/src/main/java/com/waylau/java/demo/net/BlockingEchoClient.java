/**
 * Welcome to https://waylau.com
 */
package com.waylau.java.demo.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Blocking Echo Client.
 * 
 * @since 1.0.0 2019年9月28日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public class BlockingEchoClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
            System.err.println(
                "用法: java BlockingEchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out =
                new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("不明主机，主机名为： " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("不能从主机中获取I/O，主机名为：" +
                hostName);
            System.exit(1);
        } 
	}

}
