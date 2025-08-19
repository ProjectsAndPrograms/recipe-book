package com.oranbyte.recipebook.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.dto.CommentDto;
import com.oranbyte.recipebook.entity.Comment;
import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.mapper.CommentMapper;
import com.oranbyte.recipebook.repository.CommentRepository;
import com.oranbyte.recipebook.service.CommentService;
import com.oranbyte.recipebook.specification.CommentSpecification;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public List<CommentDto> getComments(Recipe recipe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CommentDto> searchComments(String userName, String recipeTitle, Pageable pageable) {
		Specification<Comment> spec = CommentSpecification.hasUserName(userName)
				.and(CommentSpecification.hasRecipeTitle(recipeTitle));

		return commentRepository.findAll(spec, pageable).map(CommentMapper::toDto);
	}

	@Override
	public long getCommentCount(Long recipeId) {
		return commentRepository.countByRecipeId(recipeId);
	}

	@Override
	public void deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}

	@Override
	public Comment getComment(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}

	@Override
	public long getWeeklyComments(Long userId) {
		LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
		return commentRepository.countByRecipeUserIdAndCreatedAtAfter(userId, oneWeekAgo);
	}

	@Override
	public long getMonthlyComments(Long userId) {
		LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
		return commentRepository.countByRecipeUserIdAndCreatedAtAfter(userId, oneMonthAgo);
	}

	@Override
	public long getYearlyComments(Long userId) {
		LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
		return commentRepository.countByRecipeUserIdAndCreatedAtAfter(userId, oneYearAgo);
	}
}
