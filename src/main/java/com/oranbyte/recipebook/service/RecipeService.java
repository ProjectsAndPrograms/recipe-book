package com.oranbyte.recipebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.exception.UserNotFoundException;

public interface RecipeService {

	Recipe saveRecipe(Recipe recipe) throws UserNotFoundException;
	
	Recipe convertToEntity(RecipeDto dto, User user);
	
	void updateEntityFromDto(Recipe existingRecipe, RecipeDto dto, User user);
	
	List<RecipeDto> getLast20Recipes(); 
	
	Page<RecipeDto> getAllRecipes(Pageable pageable);
	
	Page<RecipeDto> recentRecipes(Pageable pageable);
	
	Page<RecipeDto> searchRecipes(Long categoryId, Long tagId, String title, String difficulty, Pageable pageable);

	Page<RecipeDto> searchRecipes(Long userId, Long categoryId, Long tagId, String title, String difficulty, Pageable pageable);
	
	int getRecipeCount(Long userid);
	
	Optional<Recipe> getRecipe(Long id);

	void deleteRecipe(Recipe recipe);
	
	Optional<Recipe> getRecipeByIdAndUser(Long id, User user);
	
	List<RecipeDto> getTop3PopularRecipesThisMonth();
	
	RecipeDto getRandomRecipe();
	
	RecipeDto getMostPopularRecipe();
	
	
}
