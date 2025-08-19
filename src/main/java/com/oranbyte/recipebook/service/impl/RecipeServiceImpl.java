package com.oranbyte.recipebook.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.entity.Category;
import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.exception.UserNotFoundException;
import com.oranbyte.recipebook.mapper.RecipeMapper;
import com.oranbyte.recipebook.repository.CategoryRepository;
import com.oranbyte.recipebook.repository.RecipeRepository;
import com.oranbyte.recipebook.service.RecipeService;
import com.oranbyte.recipebook.specification.RecipeSpecification;

@Service
public class RecipeServiceImpl implements RecipeService{

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Recipe saveRecipe(Recipe recipe) throws UserNotFoundException {
		if (recipe.getServings() <= 0) {
			recipe.setServings(1);
		}
		return recipeRepository.save(recipe);
	}

	public Page<RecipeDto> searchRecipes(Long categoryId, Long tagId, String title, String difficulty,
			Pageable pageable) {
		Specification<Recipe> spec = RecipeSpecification.hasCategory(categoryId).and(RecipeSpecification.hasTag(tagId))
				.and(RecipeSpecification.hasTitleLike(title)).and(RecipeSpecification.hasDifficulty(difficulty));

		return recipeRepository.findAll(spec, pageable).map(RecipeMapper::toDto);
	}

	@Override
	public Recipe convertToEntity(RecipeDto dto, User user) {
		Category category = categoryRepository.findById(dto.getCategoryId())
				.orElseThrow(() -> new IllegalArgumentException("Category not found"));

		return Recipe.builder().id(dto.getId()).title(dto.getTitle()).description(dto.getDescription())
				.instructions(dto.getInstructions()).prepTime(dto.getPrepTime()).cookTime(dto.getCookTime())
				.servings(dto.getServings()).difficulty(dto.getDifficulty()).user(user).category(category).build();
	}

	@Override
	public void updateEntityFromDto(Recipe existingRecipe, RecipeDto dto, User user) {
		Category category = categoryRepository.findById(dto.getCategoryId())
				.orElseThrow(() -> new IllegalArgumentException("Category not found"));

		existingRecipe.setTitle(dto.getTitle());
		existingRecipe.setDescription(dto.getDescription());
		existingRecipe.setInstructions(dto.getInstructions());
		existingRecipe.setPrepTime(dto.getPrepTime());
		existingRecipe.setCookTime(dto.getCookTime());
		existingRecipe.setServings(dto.getServings());
		existingRecipe.setDifficulty(dto.getDifficulty());
		existingRecipe.setUser(user);
		existingRecipe.setCategory(category);
	}

	@Override
	public List<RecipeDto> getLast20Recipes() {
		List<Recipe> recipes = recipeRepository.findTop20ByOrderByIdDesc();

		return recipes.stream().map(RecipeMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public Page<RecipeDto> getAllRecipes(Pageable pageable) {
		Page<Recipe> recipePage = recipeRepository.findAll(pageable);

		List<RecipeDto> recipeDtos = recipePage.getContent().stream().map(RecipeMapper::toDto).toList();

		return new PageImpl<>(recipeDtos, pageable, recipePage.getTotalElements());
	}

	@Override
	public Page<RecipeDto> recentRecipes(Pageable pageable) {
		return recipeRepository.findByOrderByIdDesc(pageable).map(RecipeMapper::toDto);
	}

	@Override
	public int getRecipeCount(Long userid) {
		return recipeRepository.countRecipesByUserId(userid);
	}

	@Override
	public Page<RecipeDto> searchRecipes(Long userId, Long categoryId, Long tagId, String title, String difficulty,
			Pageable pageable) {
		Specification<Recipe> spec = RecipeSpecification.hasUserId(userId)
				.and(RecipeSpecification.hasCategory(categoryId)).and(RecipeSpecification.hasTag(tagId))
				.and(RecipeSpecification.hasTitleLike(title)).and(RecipeSpecification.hasDifficulty(difficulty));

		return recipeRepository.findAll(spec, pageable).map(RecipeMapper::toDto);
	}

	@Override
	public Optional<Recipe> getRecipe(Long id) {
		return recipeRepository.findById(id);
	}

	@Override
	public void deleteRecipe(Recipe recipe) {
		recipeRepository.deleteById(recipe.getId());
	}

	@Override
	public Optional<Recipe> getRecipeByIdAndUser(Long id, User user) {
		return recipeRepository.findByIdAndUser(id, user);
	}

	public List<RecipeDto> getTop3PopularRecipesThisMonth() {
	    return recipeRepository.findMostPopularRecipesThisMonth(PageRequest.of(0, 3)).stream().map(RecipeMapper::toDto).toList();
	}


	@Override
	public RecipeDto getRandomRecipe() {
		return RecipeMapper.toDto(recipeRepository.findRandomRecipe());
	}

	@Override
	public RecipeDto getMostPopularRecipe() {
		return RecipeMapper.toDto(recipeRepository.findMostPopularRecipe());
	}

}
