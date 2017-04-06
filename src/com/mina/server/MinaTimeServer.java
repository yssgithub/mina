package com.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaTimeServer {

	private static final int PORT = 6488;

	public static void main(String[] args) throws IOException {

		// 监听即将到来的TCP连接，建立监控器
		IoAcceptor acceptor = new NioSocketAcceptor();
		// 设置拦截器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		// 设置处理类
		acceptor.setHandler(new TimeServerHandler());
		// 设置配置
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		// 绑定的监听端口，可多次绑定，也可同时绑定多个。
		acceptor.bind(new InetSocketAddress(PORT));
		System.out.println("服务端启动成功......端口号为： " + PORT);

	}

}
