package com.oranbyte.recipebook.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oranbyte.recipebook.entity.Subscribe;
import com.oranbyte.recipebook.service.SubscribeService;
import com.oranbyte.recipebook.service.UserAgentService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/subscribe")
public class SubscribeController {
	
	@Autowired
	private SubscribeService subscribeService;
	
	@Autowired
	private UserAgentService userAgentService;

	@PostMapping
	@ResponseBody
	public Map<String, String> save(@RequestParam("email") String email, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request){
		
		Map<String, String> response = new HashMap<>();
		
		if(subscribeService.isEmailExists(email)) {
			response.put("status", "error");
			response.put("message", "Email Already Exists!");
			return response;
		}
		
		String device = userAgentService.detectDevice(userAgent);
        String browser = userAgentService.detectBrowser(userAgent);
        String os = userAgentService.detectOS(userAgent);
        String location = userAgentService.getLocationFromIP(request.getRemoteAddr());
       
		try {
			subscribeService.save(Subscribe.builder()
					.email(email)
					.ipAddress(request.getRemoteAddr())
					.userAgent(userAgent)
					.deviceType(device)
					.browser(browser)
					.os(os)
					.location(location)
					.build());
			response.put("status", "success");
			response.put("message", "Susbcription added!");
		}catch(Exception ex) {
			response.put("status", "error");
			response.put("message", "Error : " + ex.getMessage());
		}
		return response;
	}
	
	
}
