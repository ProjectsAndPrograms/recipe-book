package com.oranbyte.recipebook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{

	Optional<Tag> findByName(String tagName);
	
	List<Tag> findTop20ByOrderByIdAsc();

}
