package com.oranbyte.recipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
