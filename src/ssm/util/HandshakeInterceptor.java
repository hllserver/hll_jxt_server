package ssm.util;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import ssm.entity.user.UserO;

/**
 * websocket 握手
 * @author liaoyun 2016-8-17
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor{
	private Logger logger = Logger.getLogger(HandshakeInterceptor.class);
	
	/**
	 * 握手前
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler arg2,
			Map<String, Object> attrbute) throws Exception {
		logger.info("beforeHandshake");
		return true;
	}
	
	/**
	 * 握手后
	 */
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		logger.info("afterHandshake");
	}
}
