package com.oranbyte.recipebook.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oranbyte.recipebook.entity.Comment;
import com.oranbyte.recipebook.entity.Recipe;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment>{

	Page<Comment> findByRecipeIdAndParentIsNull(Long recipeId, Pageable pageable);
	
	List<Comment> findByRecipe(Recipe recipe);

	long countByRecipeId(Long recipeId);
	
	Page<Comment> findByParentId(Long parentId, Pageable pageable);
	
	long countByRecipeUserIdAndCreatedAtAfter(Long userId, LocalDateTime dateTime);
	
}
