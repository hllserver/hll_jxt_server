/**
 * 排队列表聊天框内容的更新操作
 * @author heyi
 * 2016/8/16
 *
 */
package ssm.controller.android;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ssm.entity.android.MessageChat;
import ssm.service.android.MessageChatService;

@Controller
@RequestMapping(value="/messageChat")
public class MessageChatAction {

	@Autowired
	private MessageChatService messageChatService;
	@RequestMapping(value="/addMessage",method=RequestMethod.POST,produces="application/json",consumes="application/json")
	public void addMessage(@RequestBody MessageChat messageChat){
		MessageChat message=new MessageChat();
		message.setMsg(messageChat.getMsg());
		message.setPlaceName(messageChat.getPlaceName());
		message.setSchollAccount(messageChat.getSchollAccount());
		message.setUserAccount(messageChat.getUserAccount());
		messageChatService.addMessageChat(messageChat);
		
		//发送消息到每个在线用户
		
		
	}
	
}
