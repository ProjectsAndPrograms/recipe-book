package com.oranbyte.recipebook.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.dto.RecipeIngredientDto;
import com.oranbyte.recipebook.entity.Ingredient;
import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.RecipeIngredient;
import com.oranbyte.recipebook.entity.Unit;
import com.oranbyte.recipebook.mapper.RecipeIngredientMapper;
import com.oranbyte.recipebook.repository.IngredientRepository;
import com.oranbyte.recipebook.repository.RecipeIngredientRepository;
import com.oranbyte.recipebook.repository.UnitRepository;
import com.oranbyte.recipebook.service.IngredientService;

import jakarta.transaction.Transactional;

@Service
public class IngredientServiceImpl implements IngredientService{

	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;

	@Override
	public void saveRecipeIngredients(Recipe recipe, String[] names, Integer[] quantities, Long[] unitIds, String[] notes) {
		for (int i = 0; i < names.length; i++) {
	        String name = names[i].trim();

	        if (name.isEmpty()) continue;

	        Ingredient ingredient = ingredientRepository.findByName(name)
	            .orElseGet(() -> ingredientRepository.save(new Ingredient(null, name, null)));

	        Unit unit = (unitIds.length > i && unitIds[i] != null)
	            ? unitRepository.findById(unitIds[i]).orElse(null)
	            : null;

	        RecipeIngredient ri = RecipeIngredient.builder()
	            .recipe(recipe)
	            .ingredient(ingredient)
	            .quantity((quantities.length > i && quantities[i] != null) ? (quantities[i] == 0 ? null : quantities[i]) : null)
	            .unit(unit)
	            .note(notes.length > i ? notes[i] : null)
	            .build();

	        recipeIngredientRepository.save(ri);
	    }
	}

	@Transactional
    public void deleteByRecipe(Recipe recipe) {
    	recipeIngredientRepository.deleteByRecipe(recipe);
    }

	@Override
	public List<RecipeIngredientDto> getIngredients(Long recipeId) {
		return recipeIngredientRepository.findByRecipeId(recipeId).stream().map(RecipeIngredientMapper::toDto).toList();
	}
	
	

}
