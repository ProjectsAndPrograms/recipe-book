package com.oranbyte.recipebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oranbyte.recipebook.dto.CategoryDto;
import com.oranbyte.recipebook.exception.ResourceNotFoundException;
import com.oranbyte.recipebook.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/{id}")
	public String show(@PathVariable Long id) throws ResourceNotFoundException {
		
		CategoryDto dto = categoryService.getCategory(id);
		
		return "redirect:/recipes?categoryId=" + dto.getId();
	}
	
}
