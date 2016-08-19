package ssm.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import ssm.entity.common.SocketMsg;
/**
 * websocket 处理类
 * @author liaoyun 2016-8-17
 */
public class WebSocketEndPoint extends TextWebSocketHandler{
	
	private static Map<String,WebSocketSession> sessionMap = new HashMap<String,WebSocketSession>(); //保存所有用户的  WebSocketSession
	private Gson gson = new Gson();
	private Logger logger = Logger.getLogger(WebSocketEndPoint.class);
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {                //成功建立连接后
		super.afterConnectionEstablished(session);
		logger.info("Established ："  +"  ip: " + session.getRemoteAddress());
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception { //断开连接后
		logger.info("----websocket closed--------");
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception { //收到消息后
		SocketMsg skm = createSocketMsg(message);
		boolean islog = isLogin(skm);
		if(!islog){                                          //没有登陆，断开连接
			logger.warn("=====websocket :  user is not login=====");
			session.close();
		}
		if(skm.getScene()==3){                               //登陆 websocket
			sessionMap.put(skm.getAccount(), session);
			logger.info("user " + skm.getAccount() + "  login.   ip: " + session.getRemoteAddress()); 
		}
		skm.setKey(null);                                    //去掉 key,防止数据泄露
		if(skm.getScene()==SocketMsg.SCENE_CHAT &&
				skm.getType()==SocketMsg.TYPE_TRANSMIT){     //场景为chat,普通转发
			sendToUsers(skm);
		}else if(skm.getScene()==SocketMsg.SCENE_CHAT &&
				skm.getType()==SocketMsg.TYPE_BROADCAST){    //场景为chat,全部转发
			sendToAll(skm);
		}
	}
	
	/**
	 * TextMessage ---> SocketMsg  liaoyun 2016-8-19
	 * @param message
	 * @return
	 */
	private SocketMsg createSocketMsg(TextMessage message) {
		if(message == null){
			return null;
		}
		String jsonStr = byteToUtf8String(message);
		JsonElement je = new JsonParser().parse(jsonStr);
		SocketMsg t =  new Gson().fromJson(je, SocketMsg.class);
		return t;
	}

	/**
	 * 发消息给所有人 liaoyun 2016-8-18
	 * @param smg
	 */
	private void sendToAll(SocketMsg smg){
		Set<String> set = sessionMap.keySet();
		for (String s : set) {
			WebSocketSession session = sessionMap.get(s);
			if(session != null && session.isOpen()){
				sendMessage(session,socketMsgToTextMsg(smg));
			}
		}
	}
	
	/**
	 * 发送信息给某些用户 liaoyun 2016-8-18
	 * @param smg
	 */
	private void sendToUsers(SocketMsg smg){
		List<String> accounts = smg.getUsers();
		TextMessage msg = socketMsgToTextMsg(smg);
		for (String account : accounts) {
			if(account != null && !account.equals("")){
				WebSocketSession session = sessionMap.get(account.trim());
				if(session != null  && session.isOpen()){
					sendMessage(session, msg);
				}
			}
		}
	}
	/**
	 * SocketMsg 转为  TextMessage liaoyun 2016-8-19
	 * @param smg
	 * @return
	 */
	private TextMessage socketMsgToTextMsg(SocketMsg smg) {
		TextMessage tmg = new TextMessage(objectToByte(smg));
		return tmg;
	}

	/**
	 * 将对象转为byte[] liaoyun 2016-8-19
	 * @param smg
	 * @return
	 */
	private byte[] objectToByte(SocketMsg smg) {
		String jsonStr = objectToJson(smg);
		byte[] b = null;
		try {
			b = jsonStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 发送消息 liaoyun 2016-8-18
	 * @param session
	 * @param msg
	 */
	private void sendMessage(final WebSocketSession session, final TextMessage msg) {
		new Thread(){
			public void run() {
				try {
					session.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	/**
	 * 将对象转换为json字符串 liaoyun 
	 * @param object
	 * @return
	 */
	private String objectToJson(Object object){
		try{
			String str = gson.toJson(object);
			return str;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * byte[] to String
	 * @param tm
	 * @return
	 */
	private String byteToUtf8String(TextMessage tm){
		byte[] b = tm.asBytes();
		try {
			String str = new String(b, "UTF-8");
			return str;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("byte[] To Utf8 String faild");
		}
		return null;
	}
	
	private boolean isLogin(SocketMsg msg){
		if(msg == null){
			return false;
		}
		String account = msg.getAccount();
		int key = msg.getKey();
		if(CommonUtil.webSocketMap.get(account) == key){
			return true;
		}
		return false;
	}
}
