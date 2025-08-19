package com.oranbyte.recipebook.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.enums.ReactionType;
import com.oranbyte.recipebook.service.RecipeReactionService;
import com.oranbyte.recipebook.service.RecipeService;
import com.oranbyte.recipebook.service.UserService;

@Controller
@RequestMapping("/recipes")
public class RecipeReactionController {

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private UserService userService;

	@Autowired
	private RecipeReactionService reactionService;

	@PostMapping("/{id}/react/{type}")
	@ResponseBody
	public Map<String, String> react(
	        @PathVariable Long id,
	        @PathVariable ReactionType type
	) {
	    Recipe recipe = recipeService.getRecipe(id).orElse(null);
	    User user = userService.getLoginUser();
	    
	    if (recipe == null || user == null) {
	        return Map.of("status", "error", "message", "Invalid request");
	    }

	    reactionService.reactToRecipe(user, recipe, type);
	    boolean isLiked = reactionService.isReacted(user, recipe, ReactionType.LIKE);
		boolean isDisliked = reactionService.isReacted(user, recipe, ReactionType.DISLIKE);
	    
	    return Map.of(
	        "status", "success",
	        "likes", String.valueOf(reactionService.getLikeCount(recipe)),
	        "isLiked", String.valueOf(isLiked),
	        "isDisliked", String.valueOf(isDisliked)
	    );
	}


}
