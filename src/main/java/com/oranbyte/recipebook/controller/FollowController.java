package com.oranbyte.recipebook.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.service.FollowService;
import com.oranbyte.recipebook.service.UserService;

@Controller
@RequestMapping("/user")
public class FollowController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FollowService followService;
	
	@PostMapping("/{id}/follow")
	@ResponseBody
	public Map<String, Object> followUnfollow(@PathVariable Long id) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        User targetUser = userService.getUser(id);
	        User currentUser = userService.getLoginUser();

	        if (targetUser == null) {
	            throw new RuntimeException("User not found.");
	        }

	        if (currentUser.getId().equals(targetUser.getId())) {
	            throw new RuntimeException("You cannot follow yourself.");
	        }

	        boolean isFollowing;
	        if (currentUser.isFollowing(targetUser)) {
	            followService.unfollow(currentUser, targetUser);
	            isFollowing = false;
	        } else {
	            followService.follow(currentUser, targetUser);
	            isFollowing = true;
	        }

	        userService.save(currentUser);

	        response.put("status", "success");
	        response.put("isFollowing", isFollowing);
	        response.put("followersCount", targetUser.getFollowers().size());

	    } catch (Exception e) {
	        response.put("status", "error");
	        response.put("message", e.getMessage());
	    }

	    return response;
	}

	
}
