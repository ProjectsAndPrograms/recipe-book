package com.oranbyte.recipebook.controller.settings;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.service.CommentService;
import com.oranbyte.recipebook.service.PaginationService;
import com.oranbyte.recipebook.service.RecipeReactionService;
import com.oranbyte.recipebook.service.RecipeService;
import com.oranbyte.recipebook.service.UserService;

@Controller
@RequestMapping("/settings/my-recipes")
public class MyRecipeSettingsController {

	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private PaginationService paginationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipeReactionService recipeReactionService;
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping
	public String index(
			Model model,
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) Long categoryId, 
			@RequestParam(required = false) Long tagId,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String difficulty,
			Principal principal
			) {
		
		User user = userService.getLoginUser();
		
		int pageIndex = Math.max(page - 1, 0);
		Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<RecipeDto> recipePage = recipeService.searchRecipes(user.getId(), categoryId, tagId, title, difficulty, pageable);
		
		String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
	    recipePage.getContent().forEach(recipe -> {
	        recipe.setShareUrl(baseUrl + "/recipes/" + recipe.getId());
	        recipe.setLikeCount(recipeReactionService.getLikeCountByRecipeId(recipe.getId()));
	        recipe.setDislikeCount(recipeReactionService.getDislikeCountByRecipeId(recipe.getId()));
	        recipe.setCommentCount(commentService.getCommentCount(recipe.getId()));
	    });
		
		model.addAttribute("recipes", recipePage.getContent());
		model.addAllAttributes(paginationService.getPageMetadata(recipePage, page));
		model.addAttribute("currentPageDisplay", page);
		
		return "settings/my-recipes";
	}
	
}
