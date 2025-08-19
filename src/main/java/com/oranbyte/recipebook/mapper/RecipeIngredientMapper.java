package com.oranbyte.recipebook.mapper;

import com.oranbyte.recipebook.dto.RecipeIngredientDto;
import com.oranbyte.recipebook.entity.RecipeIngredient;
import com.oranbyte.recipebook.entity.Unit;

public class RecipeIngredientMapper {

	public static RecipeIngredientDto toDto(RecipeIngredient ingredient) {
		Unit unit = ingredient.getUnit();
	    return RecipeIngredientDto.builder()
	    		.name(ingredient.getIngredient().getName())
	    		.id(ingredient.getId())
	    		.quantity(String.valueOf(ingredient.getQuantity() == null ? "" : ingredient.getQuantity()))
	    		.unit(unit == null ? "" : unit.getName())
	    		.unitId(unit != null ? unit.getId() : null)
	    		.note(ingredient.getNote())
	    		.build();
	}
	
}
