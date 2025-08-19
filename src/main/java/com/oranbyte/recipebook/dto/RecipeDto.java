package com.oranbyte.recipebook.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for transferring Recipe data without exposing the full entity structure.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto {

	private Long id;

	private String title;

	private String description;

	private String instructions;

	private long prepTime;

	private long cookTime;

	private int servings;

	private String difficulty;

	private Long userId;
	
	private String userName;
	
	private String userImage;

	private Long categoryId;
	
	private String categoryName;

	private String primaryImage;

	private List<RecipeImageDto> imagesDtos;
	
	private LocalDateTime createdAt;
	
	private long likeCount;
	
	private long dislikeCount;
	
	private long commentCount;
	
	private String shareUrl;
	
}
