package Utile;

import java.util.List;

import javax.swing.JList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailUtils {

	
	@Autowired
	public JavaMailSender emailSender; 
	
	public void sendSimpleMessage(String to,String subject, String text, String List) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("btech");
		message.setText("tech.Sajid.Abbas@gamail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		if(List!=null && List.size()>0)
		message.setCc(List);
		emailSender.send(message);
	}
	
	
	
	private String[] getCcArray(List<String>ccList) {
		String[] cc = new String[ccList.size()];
		
		for(int i= 0; i<ccList.size(); i++) {
			cc[i]= ccList.get(i);
		}
		return cc;
		
		
	}

public void forgotMail(String to, String subject,String Passwoed)throws MessagingException{
	MimeMessage message = emailSender.createMimeMessage();
	MimeMessageHelper helper = new MimeMessageHelper(message,true);
	helper.setFrom("tech.sajid.Abbas@gmeli.com");
	helper.setTo(to);
	helper.setSubject(subject);
	String htmlMsg = "<p><b>your Login details for Cafe Managment System</b><br><b>Email: </b>" + to " <br><b>Password: </b>" + password+ "<br><a hrf=\"http://localHost:4200/\">Click here to login</a></p>;
	message.setContent(htmlmsg,"textHtml");
	emailSender.send(message);
}

	
}

