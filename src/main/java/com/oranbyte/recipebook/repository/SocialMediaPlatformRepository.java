package com.oranbyte.recipebook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.SocialMediaPlatform;

@Repository
public interface SocialMediaPlatformRepository extends JpaRepository<SocialMediaPlatform, Long>{

	 Optional<SocialMediaPlatform> findByNameIgnoreCase(String name);
	
}
