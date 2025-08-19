package com.oranbyte.recipebook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.UserSocialLink;

@Repository
public interface UserSocialLinkRepository extends JpaRepository<UserSocialLink, Long>{
	
	List<Optional<UserSocialLink>> findByUserId(Long userId);
	
	

}
