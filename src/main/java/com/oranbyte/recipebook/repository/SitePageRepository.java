package com.oranbyte.recipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.SitePage;

@Repository
public interface SitePageRepository extends JpaRepository<SitePage, Long>{

	SitePage findBySlug(String slug);
	
}
