package com.oranbyte.recipebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.dto.UserDetailDto;
import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.service.PaginationService;
import com.oranbyte.recipebook.service.RecipeService;
import com.oranbyte.recipebook.service.UserDetailService;
import com.oranbyte.recipebook.service.UserService;
import com.oranbyte.recipebook.service.UserSocialLinksService;

@Controller
@RequestMapping("/u")
public class ProfileController {

	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private UserSocialLinksService userSocialLinksService;
	
	@Autowired
	private PaginationService paginationService;
	
	@GetMapping("/{username}")
	public String index(
			@PathVariable String username,
			Model model,
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "15") int size,
			@RequestParam(required = false) Long categoryId, 
			@RequestParam(required = false) Long tagId,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String difficulty
			) {
		
		UserDto user = userService.getUserDto(username);
		UserDetailDto userDetail = userDetailService.getUserDetailDto(user.getId());
		
		int recipeCount = recipeService.getRecipeCount(user.getId());
		user.setRecipeCount(recipeCount);
		
		model.addAttribute("user", user);
		model.addAttribute("user_detail", userDetail);
		
		model.addAttribute("user_social_links", userSocialLinksService.getUserSocialLinks(user.getId()));
		
		int pageIndex = Math.max(page - 1, 0);
		Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<RecipeDto> recipePage = recipeService.searchRecipes(user.getId(), categoryId, tagId, title, difficulty, pageable);
		
		model.addAttribute("recipes", recipePage.getContent());
		model.addAllAttributes(paginationService.getPageMetadata(recipePage, page));
		model.addAttribute("currentPageDisplay", page);

		model.addAttribute("tagId", tagId);
		model.addAttribute("title", title);
		model.addAttribute("difficulty", difficulty);
		
		User loginUser = userService.getLoginUser();
		User thisUser = userService.getUser(user.getId());
		
		model.addAttribute("isFollowing", thisUser.isFollowedBy(loginUser));
		
		return "profile/user-profile";
	}
	
}
