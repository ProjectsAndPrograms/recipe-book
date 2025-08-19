package com.oranbyte.recipebook.service;

import java.util.List;

import com.oranbyte.recipebook.dto.CategoryDto;
import com.oranbyte.recipebook.entity.Category;
import com.oranbyte.recipebook.exception.ResourceNotFoundException;

public interface CategoryService {
	List<CategoryDto> getAllCategories();
	
	CategoryDto getCategory(Long id) throws ResourceNotFoundException;
	
	long getRecipeCount(Category category);
}
