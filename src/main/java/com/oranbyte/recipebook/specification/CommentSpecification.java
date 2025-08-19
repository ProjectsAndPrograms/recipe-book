package com.oranbyte.recipebook.specification;

import org.springframework.data.jpa.domain.Specification;

import com.oranbyte.recipebook.entity.Comment;

public class CommentSpecification {
	
	public static Specification<Comment> hasRecipeId(Long recipeId) {
        return (root, query, cb) -> 
            recipeId == null ? null : cb.equal(root.get("recipe").get("id"), recipeId);
    }

    public static Specification<Comment> hasUserId(Long userId) {
        return (root, query, cb) -> 
            userId == null ? null : cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Comment> hasParentId(Long parentId) {
        return (root, query, cb) -> 
            parentId == null ? null : cb.equal(root.get("parent").get("id"), parentId);
    }

    public static Specification<Comment> messageContains(String keyword) {
        return (root, query, cb) ->
            (keyword == null || keyword.isBlank()) ? null :
            cb.like(cb.lower(root.get("message")), "%" + keyword.toLowerCase() + "%");
    }

    public static Specification<Comment> isTopLevel() {
        return (root, query, cb) -> cb.isNull(root.get("parent"));
    }

    public static Specification<Comment> hasUserName(String userName) {
        return (root, query, cb) ->
            (userName == null || userName.isBlank()) ? null :
            cb.like(cb.lower(root.join("user").get("username")), "%" + userName.toLowerCase() + "%");
    }
    
    public static Specification<Comment> hasRecipeTitle(String recipeTitle) {
        return (root, query, cb) ->
            (recipeTitle == null || recipeTitle.isBlank()) ? null :
            cb.like(cb.lower(root.join("recipe").get("title")), "%" + recipeTitle.toLowerCase() + "%");
    }

	
}
