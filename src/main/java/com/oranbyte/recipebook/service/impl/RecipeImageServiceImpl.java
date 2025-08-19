package com.oranbyte.recipebook.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.RecipeImage;
import com.oranbyte.recipebook.repository.RecipeImageRepository;
import com.oranbyte.recipebook.service.FileService;
import com.oranbyte.recipebook.service.RecipeImageService;

@Service
public class RecipeImageServiceImpl implements RecipeImageService{

	@Autowired
	private RecipeImageRepository recipeImageRepository;
	
	@Autowired
	private FileService fileService;
	
	@Override
	public void saveAll(Recipe recipe, MultipartFile[] images) {
		if (images != null && images.length > 0) {
			boolean primary = true;
	        for (MultipartFile image : images) {
	            if (!image.isEmpty()) {
	                try {
	                    RecipeImage recipeImage = new RecipeImage();
	                    recipeImage.setImagePath(fileService.uploadFile(image, "recipes"));
	                    recipeImage.setPrimary(primary);
	                    recipeImage.setRecipe(recipe);
	                    primary = false;

	                    recipeImageRepository.save(recipeImage);

	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	}

	@Override
	public void deleteRecipeImage(RecipeImage recipeImage) {
		recipeImageRepository.delete(recipeImage);
	}

	@Override
	public Optional<RecipeImage> getRecipeImage(Long id) {
		return recipeImageRepository.findById(id);
	}

	

}
