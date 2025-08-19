package com.oranbyte.recipebook.mapper;

import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.dto.RecipeImageDto;
import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.RecipeImage;

public class RecipeMapper {

    public static RecipeDto toDto(Recipe recipe) {
    	 return RecipeDto.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .instructions(recipe.getInstructions())
                .prepTime(recipe.getPrepTime())
                .cookTime(recipe.getCookTime())
                .servings(recipe.getServings())
                .difficulty(recipe.getDifficulty())
                .userId(recipe.getUser().getId())
                .userName(recipe.getUser().getUsername())   
                .userImage(recipe.getUser().getImageUrl())   
                .categoryId(recipe.getCategory().getId())
                .categoryName(recipe.getCategory().getName())
                .primaryImage(
                        recipe.getImages().stream()
                            .filter(RecipeImage::isPrimary)
                            .map(RecipeImage::getUrl)
                            .findFirst()
                            .orElse(null)
                )
                .imagesDtos(
                        recipe.getImages().stream()
                            .map(image -> RecipeImageDto.builder().id(image.getId()).imagePath(image.getUrl()).isPrimary(image.isPrimary()).build())
                            .toList()
                    )
                .createdAt(recipe.getCreatedAt())
                .build();
    }
}
