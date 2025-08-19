package com.oranbyte.recipebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oranbyte.recipebook.api.ApiResponse;
import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.request.RegistrationRequest;
import com.oranbyte.recipebook.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("/login")
	public String loginPage(Model model, Authentication authentication) {
		if (authentication != null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return "auth/login";
	}

	@GetMapping("/register")
	public String registerPage(Model model, Authentication authentication) {
		if (authentication != null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		model.addAttribute("userDto", new UserDto());
		return "auth/register";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute RegistrationRequest request, BindingResult bindingResult, Model model,
			Authentication authentication, RedirectAttributes redirectAttr, HttpServletRequest req) {

		if (bindingResult.hasErrors()) {
			redirectAttr.addFlashAttribute("error", bindingResult.getFieldError().getDefaultMessage());
			return "redirect:" + req.getHeader("Referer");
		}
		if (authentication != null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		if (userService.existsByUsername(request.getUsername())) {
			redirectAttr.addFlashAttribute("error", "Username already taken.");
			redirectAttr.addAttribute("userDto", request);
			return "redirect:" + req.getHeader("Referer");
		}
		if (userService.existsByEmail(request.getEmail())) {
			redirectAttr.addFlashAttribute("error", "Email is already in use.");
			redirectAttr.addAttribute("userDto", request);
			return "redirect:" + req.getHeader("Referer");
		}

		userService.save(User.builder().username(request.getUsername()).email(request.getEmail())
				.password(encoder.encode(request.getPassword())).role("user").build());

		redirectAttr.addFlashAttribute("success", "Registration successful. Please login.");
		return "redirect:/login";
	}

	@GetMapping("/auth/register/check-username")
	public ResponseEntity<ApiResponse<Object>> checkUniqueUsername(@RequestParam String username) {
		if (username == null || username.trim().isEmpty()) {
			return ResponseEntity.badRequest().body(new ApiResponse<>("error", "Username cannot be empty"));
		}
		if (userService.existsByUsernameIncludingDeleted(username)) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ApiResponse<>("error", "User already exists with this username: " + username));
		}
		return ResponseEntity.ok(new ApiResponse<>("success", "Username is available: " + username));
	}

	@GetMapping("/auth/register/check-email")
	public ResponseEntity<ApiResponse<Object>> checkUniqueEmail(@RequestParam String email) {
		if (email == null || email.trim().isEmpty()) {
			return ResponseEntity.badRequest().body(new ApiResponse<>("error", "Email cannot be empty"));
		}

		if (userService.existsByEmailIncludingDeleted(email)) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ApiResponse<>("error", "User already exists with this email: " + email));
		}
		return ResponseEntity.ok(new ApiResponse<>("success", "Email is available: " + email));
	}
	
	

}
