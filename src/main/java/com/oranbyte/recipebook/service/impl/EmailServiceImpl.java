package com.oranbyte.recipebook.service.impl;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.oranbyte.recipebook.dto.EmailRequest;
import com.oranbyte.recipebook.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{

	@Value("${spring.mail.username}")
 	private String USERNAME;
 	
 	@Autowired
 	private JavaMailSender javaMailSender;
 	
 	@Autowired
 	private SpringTemplateEngine templateEngine;
	
 	@Override
 	public void send(EmailRequest req) throws MessagingException {
 	    MimeMessage message = javaMailSender.createMimeMessage();
 	    Context context = new Context();

 	    if (req.getModel() != null && !req.getModel().isEmpty()) {
 	        context.setVariables(req.getModel());
 	    }

 	    context.setVariable("username", USERNAME);
 	    String process = templateEngine.process(req.getTemplateName(), context);

 	    MimeMessageHelper helper = new MimeMessageHelper(
 	            message,
 	            MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
 	            StandardCharsets.UTF_8.name()
 	    );
 	    helper.setTo(req.getReceiver());
 	    helper.setFrom(new InternetAddress(USERNAME));
 	    helper.setSubject(req.getSubject());
 	    helper.setText(process, true);

 	    javaMailSender.send(message);
 	}

	
	

}
