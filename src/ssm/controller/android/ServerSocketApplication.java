package ssm.controller.android;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * 服务器 WebSocket
 * @author liaoyun 2016-8-17
 */
public class ServerSocketApplication extends WebSocketServer{

	private static Logger logger = Logger.getLogger(ServerSocketApplication.class);
	private static int serverPort = 8888;
	
	public ServerSocketApplication() throws UnknownHostException {
		super();
	}
	
	public ServerSocketApplication(int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
	}
	
	public ServerSocketApplication(InetSocketAddress address) throws UnknownHostException {
		super(address);
	}
	
	@Override
	public void onClose(WebSocket conn, int arg1, String str, boolean bl) {
		logger.info("close...");
	}

	@Override
	public void onError(WebSocket conn, Exception exc) {
		logger.info("error...");
	}

	@Override
	public void onMessage(WebSocket conn, String str) {
		logger.info("message...");
		conn.send("server get message :"+str);
		logger.info("client say: "+str);
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		logger.info("opening...");
	}
	
	public static void main(String[] args) {
		System.out.println("server socket 启动了。。。。。。。");
		try {
			ServerSocketApplication serverSocket = new ServerSocketApplication(8887);
			serverSocket.start();
			System.out.println("server socket 启动完成了。。。。。。。");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
