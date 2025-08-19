package com.oranbyte.recipebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.dto.TagDto;
import com.oranbyte.recipebook.service.RecipeReactionService;
import com.oranbyte.recipebook.service.RecipeService;
import com.oranbyte.recipebook.service.TagService;

@Controller
@RequestMapping(path = "/")
public class HomeController {
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private RecipeReactionService recipeReactionService;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping
	public String index(Model model) {
		
		List<RecipeDto> recipes = recipeService.getLast20Recipes();
		List<TagDto> tags = tagService.getTop20Tags().stream().limit(15).toList();
			
		List<RecipeDto> top3PopularRecipes = recipeService.getTop3PopularRecipesThisMonth();
		RecipeDto randomRecipe = recipeService.getRandomRecipe();
		RecipeDto mostPopularRecipe = recipeService.getMostPopularRecipe();
		
		long mostPopularRecipeLikeCount = recipeReactionService.getLikeCountByRecipeId(mostPopularRecipe.getId());
		mostPopularRecipe.setLikeCount(mostPopularRecipeLikeCount);
		
		model.addAttribute("recipes", recipes);
		model.addAttribute("top3PopularRecipes", top3PopularRecipes);
		model.addAttribute("randomRecipe", randomRecipe);
		model.addAttribute("mostPopularRecipe", mostPopularRecipe);
		model.addAttribute("tags", tags);
		
		
		return "index";
	}

}
