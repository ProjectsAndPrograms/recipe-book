package com.oranbyte.recipebook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oranbyte.recipebook.entity.Subscribe;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long>{

	Optional<Subscribe> findByEmail(String email);
	
}
