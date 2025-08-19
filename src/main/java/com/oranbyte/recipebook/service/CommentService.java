package com.oranbyte.recipebook.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oranbyte.recipebook.dto.CommentDto;
import com.oranbyte.recipebook.entity.Comment;
import com.oranbyte.recipebook.entity.Recipe;

public interface CommentService {

	Comment save(Comment comment);

	Comment getComment(Long commentId);

	List<CommentDto> getComments(Recipe recipe);

	Page<CommentDto> searchComments(String userName, String recipeTitle, Pageable pageable);

	long getCommentCount(Long recipeId);

	void deleteComment(Long commentId);

	long getWeeklyComments(Long userId);

	long getMonthlyComments(Long userId);

	long getYearlyComments(Long userId);

}
