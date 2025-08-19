package com.oranbyte.recipebook.mapper;

import java.util.List;

import com.oranbyte.recipebook.dto.CommentDto;
import com.oranbyte.recipebook.entity.Comment;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.util.DateUtil;

public class CommentMapper {
	public static CommentDto toDto(Comment comment) {
	    List<Comment> allReplies = comment.getReplies();
	    int limit = 2;

	    List<CommentDto> limitedReplies = allReplies.stream()
	        .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
	        .limit(limit)
	        .map(CommentMapper::toShallowDto)
	        .toList();

	    boolean hasMoreReplies = allReplies.size() > limit;

	    return CommentDto.builder()
	            .id(comment.getId())
	            .message(comment.getMessage())
	            .createdAt(comment.getCreatedAt())
	            .createdTimeAgo(DateUtil.timeAgo(comment.getCreatedAt()))
	            .user(UserMapper.toDto(comment.getUser()))
	            .recipe(RecipeMapper.toDto(comment.getRecipe()))
	            .parent(comment.getParent() != null ? toShallowDto(comment.getParent()) : null)
	            .replies(limitedReplies)
	            .hasMoreReplies(hasMoreReplies)
	            .build();
	}



    public static CommentDto toShallowDto(Comment comment) {
    	User user = comment.getUser();
        return CommentDto.builder()
                .id(comment.getId())
                .message(comment.getMessage())
                .user(UserMapper.toDto(user))
                .createdTimeAgo(DateUtil.timeAgo(comment.getCreatedAt()))
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
