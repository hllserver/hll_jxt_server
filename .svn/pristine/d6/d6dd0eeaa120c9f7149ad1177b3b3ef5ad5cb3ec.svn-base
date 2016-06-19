package ssm.util;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮件相关
 * @author liaoyun
 * 2016-4-4
 */
public class Email {
	/**
	 * @author liaoyun 2016-4-4
	 * @param smtpHost 服务器地址
	 * @param from     发信人地址
	 * @param fromUserPassword 发信人密码
	 * @param to   收信人地址
	 * @param subject  邮件主题
	 * @param messageText  邮件文字信息
	 * @param messageType  邮件类型？？
	 * @throws MessagingException
	 */
	 @SuppressWarnings("static-access")
	public static void sendMessage(String smtpHost, String from,  
	            String fromUserPassword, String to, String subject,  
	            String messageText, String messageType) throws MessagingException {
		  
	        // 第一步：配置javax.mail.Session对象  
	        System.out.println("为" + smtpHost + "配置mail session对象");  
	          
	          
	        Properties props = new Properties();  
	        props.put("mail.smtp.host", smtpHost);  
	        //props.put("mail.smtp.starttls.enable","true");//使用 STARTTLS安全连接  
	        props.put("mail.smtp.port", "25");             //google使用465或587端口  
	        props.put("mail.smtp.auth", "true");        // 使用验证  
	        //props.put("mail.debug", "true");  
	        Session mailSession = Session.getInstance(props,new MyAuthenticator(from,fromUserPassword));  
	  
	        // 第二步：编写消息  
	        System.out.println("编写消息from——to:" + from + "——" + to);  
	  
	        InternetAddress fromAddress = new InternetAddress(from);  
	        InternetAddress toAddress = new InternetAddress(to);  
	  
	        MimeMessage message = new MimeMessage(mailSession);  
	  
	        message.setFrom(fromAddress);  
	        message.addRecipient(RecipientType.TO, toAddress);  
	  
	        message.setSentDate(Calendar.getInstance().getTime());  
	        message.setSubject(subject);  
	        message.setContent(messageText, messageType);  
	  
	        // 第三步：发送消息  
	        Transport transport = mailSession.getTransport("smtp");  
	        transport.connect(smtpHost,"chaofeng19861126", fromUserPassword);  
	        transport.send(message, message.getRecipients(RecipientType.TO));  
	        System.out.println("message yes");  
	    }   
}
