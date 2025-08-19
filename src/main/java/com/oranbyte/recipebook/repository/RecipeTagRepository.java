package com.oranbyte.recipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.RecipeTag;

@Repository
public interface RecipeTagRepository extends JpaRepository<RecipeTag, Long>{
	
}
