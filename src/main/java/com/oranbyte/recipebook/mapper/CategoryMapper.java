package com.oranbyte.recipebook.mapper;

import com.oranbyte.recipebook.dto.CategoryDto;
import com.oranbyte.recipebook.entity.Category;

public class CategoryMapper {
	
	public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
	            .id(category.getId())
	            .name(category.getName())
	            .description(category.getDescription())
	            .recipeCount(category.getRecipes().size())
	            .build();
    }
}
