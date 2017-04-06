package com.mina.server;

import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.proxy.utils.ByteUtilities;

import com.mina.castutils.CastUtils;

public class TimeServerHandler extends IoHandlerAdapter {
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("服务端与客户端创建连接...");
		super.sessionCreated(session);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("服务端与客户端连接打开...");
		super.sessionOpened(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("服务端与客户端连接关闭...");
		super.sessionClosed(session);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("服务端发送信息成功..." + message.toString());
		super.messageSent(session, message);
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.out.println("服务端发送异常..." + cause.getMessage());
		cause.printStackTrace();
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
//		String strMsg = message.toString();
//		System.out.println("服务端接收到的数据为: " + strMsg);
//		if (strMsg.trim().equalsIgnoreCase("quit")) {
//			session.close();
//			return;
//		}
//		Date date = new Date();
//		session.write(date.toString());
		
		
		String strMsg = message.toString();
		
		
		IoBuffer bbuf = CastUtils.stringToIoBuffer(strMsg);
		byte[] byten = new byte[bbuf.limit()];
		bbuf.get(byten, bbuf.position(), bbuf.limit());
		//System.out.println("收到客户端发来的消息[byte]" + new String(byten));
		System.out.println("收到客户端发来的消息[aHex]" + ByteUtilities.asHex(byten));
		session.write(new Date().toString());
		
		

//		IoBuffer bbuf = (IoBuffer) message;
//		byte[] byten = new byte[bbuf.limit()];
//		bbuf.get(byten, bbuf.position(), bbuf.limit());
//		String msg1=new String(byten);
//		System.out.println("收到客户端发来的消息为" + "  " + msg1);
//		
//		IoBuffer ioBuffer = (IoBuffer)message;   
//	    byte[] b = new byte[ioBuffer.limit()];   
//	    ioBuffer.get(b);  
//	    String msg=new String(b);
//	    System.out.println("收到客户端发来的消息为" + "  " + msg);
//	  //将测试消息会送给客户端
//	    session.write(msg.length());
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println("服务端进入空闲状态... " + session.getIdleCount(status));
	}

}
