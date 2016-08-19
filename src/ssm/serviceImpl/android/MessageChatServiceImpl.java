package ssm.serviceImpl.android;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.entity.android.MessageChat;
import ssm.orm.android.MessageChatDao;
import ssm.service.android.MessageChatService;

@Service
public class MessageChatServiceImpl implements MessageChatService{

	@Autowired
	private MessageChatDao messageDao;
	@Override
	public void addMessageChat(MessageChat messageChat) {
		messageDao.addMessageChat(messageChat);
		
	}

}
