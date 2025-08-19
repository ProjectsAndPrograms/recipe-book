package com.oranbyte.recipebook.specification;

import org.springframework.data.jpa.domain.Specification;

import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.Tag;

import jakarta.persistence.criteria.Join;

public class RecipeSpecification {

	public static Specification<Recipe> hasUserId(Long userId) {
	    return (root, query, cb) ->
	            userId == null ? null : cb.equal(root.get("user").get("id"), userId);
	}
	
    public static Specification<Recipe> hasCategory(Long categoryId) {
        return (root, query, cb) ->
                categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Recipe> hasTag(Long tagId) {
        return (root, query, cb) -> {
            if (tagId == null) return null;
            Join<Recipe, Tag> tags = root.join("tags");
            return cb.equal(tags.get("id"), tagId);
        };
    }

    public static Specification<Recipe> hasTitleLike(String title) {
        return (root, query, cb) ->
                (title == null || title.isBlank()) ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Recipe> hasDifficulty(String difficulty) {
        return (root, query, cb) ->
                (difficulty == null || difficulty.isBlank()) ? null : cb.equal(root.get("difficulty"), difficulty);
    }
}