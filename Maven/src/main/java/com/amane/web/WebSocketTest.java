package com.amane.web;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.amane.enums.WebSocketEnum;

/*
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 *                 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
public class WebSocketTest {
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();

	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	//与某个客户端通信的次数
	private int communicationTimes = 0;
	
	//某个客户端对应的终止线程
	private Thread thread ;
	
	//某个客户端对应的终止时间;
	private Date deadline;
	
	/*
	 * 连接建立成功调用的方法
	 * 
	 * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this);
		addOnlineCount(); 
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	/*
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从set中删除
		//清除线程,终止计时器
		if(thread != null) {
			thread.interrupt();
			System.out.println(thread.getState());
		}
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/*
	 * 收到客户端消息后调用的方法(单发)
	 * 
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 * @throws IOException 
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
	//	System.out.println("来自客户端的消息:" + message);
		Object reply = null;
		//单发消息,此后默认接收到心跳
		if(communicationTimes == 0) {
			try {
				//第一次接收考试时长请求
				int minutes = Integer.parseInt(message);
				//设置deadline
				deadline = new Date(new Date().getTime() + minutes * 60 * 1000);
				thread = new Thread(() ->  {
					try {
						//设置考试结束请求(deadline)
						Thread.sleep(minutes * 60 * 1000);
						System.out.println(thread.getState());
						this.sendMessage(JSON.toJSONString(WebSocketEnum.TIMEOUT));
					} catch (InterruptedException | IOException e) {
						//考试提前结束请求
						System.out.println("interuppted");
					}
				});
				thread.start();
				reply = WebSocketEnum.DURATION_RECORDED;
			} catch(NumberFormatException e) {
				//收到的不是时长请求
				reply = WebSocketEnum.INVALID_DURATION;
			}
		}
		else {
			try {
				//收到请求考试剩余时间请求
				Double.parseDouble(message);
				reply = deadline.getTime() - new Date().getTime();
			}catch(NumberFormatException e) {
				//收到心跳请求
				reply = WebSocketEnum.PING_RECEIVED;
			}
		}
		this.sendMessage(JSON.toJSONString(reply));
	}
	
	/*
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/*
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		session.getBasicRemote().sendText(message);
		++communicationTimes;
	//	System.out.println("当前会话次数:" + communicationTimes);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocketTest.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocketTest.onlineCount--;
	}
};