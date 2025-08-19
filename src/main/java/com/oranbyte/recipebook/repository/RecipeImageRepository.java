package com.oranbyte.recipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.RecipeImage;

@Repository
public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long>{

	void deleteByIdAndRecipeId(Long id, Recipe recipe);
	
	
}
