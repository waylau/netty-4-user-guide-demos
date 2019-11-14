package com.waylau.java.demo.reactor.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO Client
 * 
 * @since 1.0.0 2019年11月14日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class NIOClient implements Runnable {

    private Selector selector;

    private SocketChannel socketChannel;

    NIOClient(String ip, int port) {
        try {
            selector = Selector.open(); //打开一个Selector
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false); //设置为非阻塞模式
            socketChannel.connect(new InetSocketAddress(ip, port)); //连接服务
            
            //入口，最初给一个客户端channel注册上去的事件都是连接事件
            SelectionKey sk = socketChannel.register(selector, SelectionKey.OP_CONNECT);
            
            //附加处理类，第一次初始化放的是连接就绪处理类
            sk.attach(new Connector(socketChannel, selector));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
            	 //就绪事件到达之前，阻塞
                selector.select();
                
                //拿到本次select获取的就绪事件
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> it = selected.iterator();
                while (it.hasNext()) {
                    //这里进行任务分发
                    dispatch((SelectionKey) (it.next()));
                }
                selected.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void dispatch(SelectionKey k) {
    	// 附带对象为Connector（
        Runnable r = (Runnable) (k.attachment()); 
        
        //调用之前注册的回调对象
        if (r != null) {
            r.run();
        }
    }
}
