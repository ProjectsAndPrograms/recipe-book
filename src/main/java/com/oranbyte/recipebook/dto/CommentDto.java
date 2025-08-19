package com.oranbyte.recipebook.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

	private Long id;
	private String message;
	private Long recipeId;
	
	private CommentDto parent;
	private UserDto user;
	private RecipeDto recipe;
	private List<CommentDto> replies;
	private LocalDateTime createdAt;
	private String createdTimeAgo;
	private boolean hasMoreReplies;
	

}
