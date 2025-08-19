package com.oranbyte.recipebook.service;

import com.oranbyte.recipebook.dto.EmailRequest;

import jakarta.mail.MessagingException;

public interface EmailService {

	void send(EmailRequest req) throws MessagingException;

}
