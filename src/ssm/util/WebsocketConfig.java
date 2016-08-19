package ssm.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@EnableWebMvc  //这个注解可以不加，如果要加，要extends WebMvcConfigurerAdapter
@EnableWebSocket
public class WebsocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		 registry.addHandler(chatMessageHandler(),"/websocket/ly.action").addInterceptors(new HandshakeInterceptor());
	     //registry.addHandler(chatMessageHandler(), "/sockjs/chatMessageServer.do").addInterceptors(new HandshakeInterceptor()).withSockJS();
		
	}
	@Bean
    public TextWebSocketHandler chatMessageHandler(){
        return new WebSocketEndPoint();
    }
}
