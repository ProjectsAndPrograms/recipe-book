package com.oranbyte.recipebook.controller.settings;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.SocialMediaPlatform;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.entity.UserDetail;
import com.oranbyte.recipebook.entity.UserSocialLink;
import com.oranbyte.recipebook.exception.UserNotFoundException;
import com.oranbyte.recipebook.repository.SocialMediaPlatformRepository;
import com.oranbyte.recipebook.repository.UserRepository;
import com.oranbyte.recipebook.service.AuthService;
import com.oranbyte.recipebook.service.FileService;
import com.oranbyte.recipebook.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/settings/profile")
public class ProfileSettingsController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private SocialMediaPlatformRepository socialMediaPlatformRepository;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping
	public String index(Model model, Principal principal) throws UserNotFoundException {
	   
	    UserDto user = userService.getUserDto(principal.getName());
		model.addAttribute("profileuser", user);
		
		System.out.println(user+ "  "+ principal.getName());
		return "settings/profile";
	}
	
	@PostMapping("/update")
	public String updateProfile(@ModelAttribute UserDto form, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes) throws UserNotFoundException {
	   
		User user = userService.getUser(principal.getName());
	    
	    user.setName(form.getName());
	    user.setEmail(form.getEmail());

	    if (user.getUserDetail() == null) {
	        user.setUserDetail(new UserDetail());
	        user.getUserDetail().setUser(user);
	    }
	    user.getUserDetail().setBio(form.getBio());
	    user.getUserDetail().setWebsiteUrl(form.getUrl());

	    List<UserSocialLink> updatedLinks = new ArrayList<>();
	    form.getSocialLinks().forEach((platformName, url) -> {
	        if (url != null && !url.isBlank()) {
	            SocialMediaPlatform platform = socialMediaPlatformRepository.findByNameIgnoreCase(platformName)
	                .orElseThrow(() -> new RuntimeException("Platform not found: " + platformName));
	            updatedLinks.add(UserSocialLink.builder()
	                .user(user)
	                .platform(platform)
	                .url(url)
	                .build());
	        }
	    });
	    
	    user.getSocialLinks().clear();
	    user.getSocialLinks().addAll(updatedLinks);

	    userRepository.save(user);
	    authService.login(user);
	    
	    redirectAttributes.addFlashAttribute("success", "Your profile updated successfully!");
	    return "redirect:" + request.getHeader("Referer");
	}

	@PostMapping("/update-image")
	@ResponseBody
	public Map<String, String> saveProfilePicture(@RequestParam("file") MultipartFile file, Principal principal) {
	    Map<String, String> response = new HashMap<>();

	    if (file == null || file.isEmpty()) {
	        response.put("status", "error");
	        response.put("message", "No file selected");
	        return response;
	    }

	    try {

	        String username = principal.getName();
	        User user = userService.getUser(username);
	        
	        if (user.getImage() != null) {
	            fileService.deleteIfExists(user.getImage());
	        }
	        String uploadFile = fileService.uploadFile(file, "pofile");

	        user.setImage(uploadFile);
	        userService.save(user);
	        response.put("status", "success");
	        response.put("message", "Profile picture updated");
	        response.put("imageUrl", "/uploads/" + uploadFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	        response.put("status", "error");
	        response.put("message", "Failed to upload image");
	    }

	    return response;
	}

	
	
	
	
}
