package com.oranbyte.recipebook.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oranbyte.recipebook.dto.UserDetailDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.entity.UserDetail;
import com.oranbyte.recipebook.service.UserDetailService;
import com.oranbyte.recipebook.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user-detail")
public class UserDetailController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@PostMapping("/save-banner")
	@ResponseBody
	public Map<String, String> saveBannerImage(@RequestParam("file") MultipartFile file, Principal principal) {
		Map<String, String> response = new HashMap<>();

		if (file == null || file.isEmpty()) {
			response.put("status", "error");
			response.put("message", "No file selected");
			return response;
		}

		try {

			String username = principal.getName();
			User user = userService.getUser(username);
			UserDetailDto userDetail = userDetailService.saveBannerImage(user, file);
			
			response.put("status", "success");
			response.put("message", "Profile picture updated");
			response.put("imageUrl", userDetail.getBannerImage());
		} catch (IOException e) {
			e.printStackTrace();
			response.put("status", "error");
			response.put("message", "Failed to upload image");
		} catch(Exception e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
		}

		return response;
	}
	
	@PostMapping("/{id}/delete-banner")
	public String deleteBannerImage(@PathVariable("id")Long id, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttr) {
		
		UserDetail userDetail = userDetailService.getUserDetail(id);
		System.out.println(userDetail.toString()+" "+ id+" "+ userDetail.getBannerImage());
		if(userDetail.getBannerImage() != null) {
			System.out.println("Here1");
			userDetail = userDetailService.deleteBannerImage(userDetail);
			userDetailService.saveUserDetail(userDetail);
			redirectAttr.addFlashAttribute("success", "Banner Removed Successfully!");
		}else {
			System.out.println("Here2");
			redirectAttr.addFlashAttribute("success", "There is no banner image set!");
		}
		
		
		return "redirect:" + request.getHeader("Referer");
	}

}
