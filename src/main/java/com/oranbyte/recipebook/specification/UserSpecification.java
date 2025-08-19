package com.oranbyte.recipebook.specification;

import org.springframework.data.jpa.domain.Specification;

import com.oranbyte.recipebook.entity.User;

public class UserSpecification {
	
    public static Specification<User> hasUsernameLike(String username) {
        return (root, query, cb) ->
                (username == null || username.isBlank()) ? null : cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }
    public static Specification<User> hasNameLike(String name) {
    	return (root, query, cb) ->
    	(name == null || name.isBlank()) ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
    public static Specification<User> hasEmailLike(String email) {
    	return (root, query, cb) ->
    	(email == null || email.isBlank()) ? null : cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

   
}
