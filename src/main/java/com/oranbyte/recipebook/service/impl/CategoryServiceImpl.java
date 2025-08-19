package com.oranbyte.recipebook.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.dto.CategoryDto;
import com.oranbyte.recipebook.entity.Category;
import com.oranbyte.recipebook.exception.ResourceNotFoundException;
import com.oranbyte.recipebook.mapper.CategoryMapper;
import com.oranbyte.recipebook.repository.CategoryRepository;
import com.oranbyte.recipebook.repository.RecipeRepository;
import com.oranbyte.recipebook.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Override
	public List<CategoryDto> getAllCategories() {
		return categoryRepository.findAll().stream()
		        .map(CategoryMapper::toDto)
		        .collect(Collectors.toList());
	}

	@Override
	public CategoryDto getCategory(Long id) throws ResourceNotFoundException {
	    Category category = categoryRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
	    
	    return CategoryMapper.toDto(category);
	}

	@Override
	public long getRecipeCount(Category category) {
		return recipeRepository.countByCategory(category);
	}

}
