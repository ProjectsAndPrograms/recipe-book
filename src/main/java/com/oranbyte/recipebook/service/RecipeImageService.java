package com.oranbyte.recipebook.service;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.RecipeImage;

public interface RecipeImageService {

	public void saveAll(Recipe recipe, MultipartFile[] images);

	public void deleteRecipeImage(RecipeImage recipeImage);

	Optional<RecipeImage> getRecipeImage(Long id);
	
}
