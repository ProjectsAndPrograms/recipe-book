package com.oranbyte.recipebook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.UserDetail;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long>{

	Optional<UserDetail> findByUserId(Long id);
	
}
