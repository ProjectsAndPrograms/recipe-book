package com.oranbyte.recipebook.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oranbyte.recipebook.dto.CategoryDto;
import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.service.CategoryService;
import com.oranbyte.recipebook.service.PaginationService;
import com.oranbyte.recipebook.service.RecipeService;
import com.oranbyte.recipebook.service.TagService;

@Controller
@RequestMapping("/recipes/search")
public class SearchController {

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private TagService tagService;

	@Autowired
	private PaginationService paginationService;

	@GetMapping
	public String index(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") int size,
			@RequestParam(required = false) Long categoryId, @RequestParam(required = false) Long tagId,
			@RequestParam(required = false) String title, @RequestParam(required = false, name = "s") String search,
			@RequestParam(required = false) String difficulty, Model model) {

		title = title == null ? search : title;
		int pageIndex = Math.max(page - 1, 0);

		Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<RecipeDto> recipePage = recipeService.searchRecipes(categoryId, tagId, title, difficulty, pageable);

		model.addAttribute("recipes", recipePage.getContent());
		model.addAllAttributes(paginationService.getPageMetadata(recipePage, pageIndex));
		model.addAttribute("currentPageDisplay", page);
		
		model.addAttribute("tagId", tagId);
		model.addAttribute("title", title);
		model.addAttribute("difficulty", difficulty);
		model.addAttribute("search", search);

		List<CategoryDto> categories = categoryService.getAllCategories();
		CategoryDto currentCategory = categories.stream().filter(category -> category.getId().equals(categoryId))
				.findFirst().orElse(null);

		model.addAttribute("categoryId", categoryId);
		model.addAttribute("category", currentCategory);
		model.addAttribute("categories", categories);

		if (Objects.nonNull(tagId)) {
			model.addAttribute("tag", tagService.getTag(tagId));
		}

		return "search/recipe-search";

	}

}
