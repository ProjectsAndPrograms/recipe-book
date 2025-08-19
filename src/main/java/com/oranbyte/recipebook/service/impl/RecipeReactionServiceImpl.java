package com.oranbyte.recipebook.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.RecipeReaction;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.enums.ReactionType;
import com.oranbyte.recipebook.repository.RecipeReactionRepository;
import com.oranbyte.recipebook.service.RecipeReactionService;

@Service
public class RecipeReactionServiceImpl implements RecipeReactionService{

	@Autowired
	private RecipeReactionRepository recipeReactionRepository;
	
	@Override
	public void reactToRecipe(User user, Recipe recipe, ReactionType type) {
		recipeReactionRepository.findByUserAndRecipe(user, recipe)
		.ifPresentOrElse(reaction -> {
			
			
			if(reaction.getReactionType() == type) {
				recipeReactionRepository.delete(reaction);
			}else {
				reaction.setReactionType(type);
				recipeReactionRepository.save(reaction);
			}
		}, 
		()->{
			RecipeReaction reaction = RecipeReaction.builder()
					.user(user)
					.recipe(recipe)
					.reactionType(type)
					.build();
			recipeReactionRepository.save(reaction);
		});
	}

	@Override
	public long getLikeCount(Recipe recipe) {
		return recipeReactionRepository.countByRecipeAndReactionType(recipe, ReactionType.LIKE);
	}

	@Override
	public long getDislikeCount(Recipe recipe) {
		return recipeReactionRepository.countByRecipeAndReactionType(recipe, ReactionType.DISLIKE);
	}
	
	public boolean isReacted(User user, Recipe recipe, ReactionType type) {
	    return recipeReactionRepository.findByUserAndRecipe(user, recipe)
	            .map(r -> r.getReactionType() == type)
	            .orElse(false);
	}

	@Override
	public long getLikeCountByRecipeId(Long recipeId) {
		return recipeReactionRepository.countByRecipeIdAndReactionType(recipeId, ReactionType.LIKE);
	}

	@Override
	public long getDislikeCountByRecipeId(long recipeId) {
		return recipeReactionRepository.countByRecipeIdAndReactionType(recipeId, ReactionType.DISLIKE);
	}

	@Override
	public long getLikeCountByUserId(Long userId) {
		return recipeReactionRepository.countByUserIdAndReactionType(userId, ReactionType.LIKE);
	}

	@Override
	public long getDislikeCountByUserId(Long userId) {
		return recipeReactionRepository.countByUserIdAndReactionType(userId, ReactionType.DISLIKE);
	}
	
	 public long getWeeklyCount(Long userId, ReactionType type) {
	        LocalDateTime startOfWeek = LocalDate.now()
	                .with(java.time.DayOfWeek.MONDAY)
	                .atStartOfDay();
	        return recipeReactionRepository.countByUserIdAndReactionTypeAndCreatedAtAfter(userId, type, startOfWeek);
	    }

	    public long getMonthlyCount(Long userId, ReactionType type) {
	        LocalDateTime startOfMonth = LocalDate.now()
	                .withDayOfMonth(1)
	                .atStartOfDay();
	        return recipeReactionRepository.countByUserIdAndReactionTypeAndCreatedAtAfter(userId, type, startOfMonth);
	    }

	    public long getYearlyCount(Long userId, ReactionType type) {
	        LocalDateTime startOfYear = LocalDate.now()
	                .withDayOfYear(1)
	                .atStartOfDay();
	        return recipeReactionRepository.countByUserIdAndReactionTypeAndCreatedAtAfter(userId, type, startOfYear);
	    }

}
