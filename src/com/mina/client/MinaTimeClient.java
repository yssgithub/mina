package com.mina.client;

import java.net.InetSocketAddress;
import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaTimeClient {
	
	private static final int PORT = 6488;
	private static IoConnector connector;
	private static IoSession session;

	public static void main(String[] args) throws Exception {

		TimeClientHandler clientHandler = new TimeClientHandler();
		connector = new NioSocketConnector();
		// 设置处理类
		connector.setHandler(clientHandler);
		ConnectFuture connFuture = connector.connect(new InetSocketAddress("localhost", PORT));
		connFuture.awaitUninterruptibly();
		session = connFuture.getSession();
		clientHandler.sessionOpened(session);
		System.out.println("TCP 客户端启动");

		for (int j = 0; j < 5; j++) { // 发送五遍
			byte[] bts = new byte[20];
			for (int i = 0; i < 20; i++) {
				bts[i] = (byte) i;
			}
			IoBuffer buffer = IoBuffer.allocate(20);
			// 自动扩容
			buffer.setAutoExpand(true);
			// 自动收缩
			buffer.setAutoShrink(true);
			buffer.put(bts);
			buffer.flip();
			session.write(buffer);

			Thread.sleep(2000);
		}
		// 关闭会话，待所有线程处理结束后
		connector.dispose(true);

	}
}
