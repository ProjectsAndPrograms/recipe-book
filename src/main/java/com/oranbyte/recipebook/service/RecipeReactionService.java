package com.oranbyte.recipebook.service;

import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.enums.ReactionType;

public interface RecipeReactionService {
	
	void reactToRecipe(User user, Recipe recipe, ReactionType type);
	
	long getLikeCount(Recipe recipe);
	
	long getDislikeCount(Recipe recipe);
	
	long getLikeCountByRecipeId(Long recipeId);
	
	long getDislikeCountByRecipeId(long recipeId);
	
	long getLikeCountByUserId(Long userId);
	
	long getDislikeCountByUserId(Long userId);
	
	boolean isReacted(User user, Recipe recipe, ReactionType type);
	
	long getWeeklyCount(Long userId, ReactionType type);
	
	long getMonthlyCount(Long userId, ReactionType type);
	
	long getYearlyCount(Long userId, ReactionType type);

	
}
