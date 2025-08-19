package com.oranbyte.recipebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.RecipeIngredient;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long>{

	void deleteByRecipe(Recipe recipe);

	List<RecipeIngredient> findByRecipeId(Long recipeId);
	
}
