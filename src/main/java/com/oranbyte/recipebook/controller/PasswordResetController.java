package com.oranbyte.recipebook.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oranbyte.recipebook.dto.EmailRequest;
import com.oranbyte.recipebook.entity.PasswordResetToken;
import com.oranbyte.recipebook.repository.PasswordResetTokenRepository;
import com.oranbyte.recipebook.service.EmailService;
import com.oranbyte.recipebook.service.UserService;

@Controller
public class PasswordResetController {

	@Autowired
	private PasswordResetTokenRepository tokenRepo;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	@GetMapping("/forgot-password")
	public String showForgotPasswordForm() {
		return "auth/forgot-password";
	}

	@PostMapping("/forgot-password")
	public String processForgotPassword(@RequestParam String email, Model model) {
		String token = UUID.randomUUID().toString();
		PasswordResetToken resetToken = new PasswordResetToken();
		resetToken.setEmail(email);
		resetToken.setToken(token);
		resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
		tokenRepo.save(resetToken);

		String resetLink = "http://localhost:8080/reset-password?token=" + token;

		EmailRequest req = EmailRequest.builder().receiver(email).subject("Password Reset Request")
				.templateName("emails/reset-password").model(Map.of("resetLink", resetLink)).build();

		try {
			emailService.send(req);
			model.addAttribute("success", "Password reset email sent!");
		} catch (Exception e) {
			model.addAttribute("error", "Error sending email!");
		}

		return "auth/forgot-password";
	}

	@GetMapping("/reset-password")
	public String showResetForm(@RequestParam String token, Model model) {
		var tokenOpt = tokenRepo.findByToken(token);
		if (tokenOpt.isEmpty() || tokenOpt.get().isExpired()) {
			model.addAttribute("error", "Invalid or expired token");
			return "auth/reset-password";
		}
		model.addAttribute("token", token);
		return "auth/reset-password";
	}

	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String token, @RequestParam String password, Model model) {
		var tokenOpt = tokenRepo.findByToken(token);
		if (tokenOpt.isEmpty() || tokenOpt.get().isExpired()) {
			model.addAttribute("error", "Invalid or expired token");
			return "auth/reset-password";
		}

		PasswordResetToken resetToken = tokenOpt.get();
		userService.updatePassword(resetToken.getEmail(), password);

		tokenRepo.delete(resetToken);
		model.addAttribute("success", "Password updated successfully!");
		return "auth/login";
	}
}
