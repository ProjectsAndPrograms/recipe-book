package com.oranbyte.recipebook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,  JpaSpecificationExecutor<User> {
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	boolean existsByEmail(String email);
	
	boolean existsByUsername(String username);
	
	@Query(value = "SELECT DISTINCT u.* FROM users u JOIN recipe r ON u.id = r.user_id ORDER BY RAND() LIMIT 3", nativeQuery = true)
	List<User> findThreeRandomUsersWithRecipes();
	
	Optional<User> findByUsernameOrEmail(String username, String email);
	
	boolean existsByUsernameAndRole(String username, String role);
	
	@Query(value = "SELECT COUNT(*) FROM users WHERE username = :username", nativeQuery = true)
	long countByUsernameIncludingDeleted(String username);

	@Query(value = "SELECT COUNT(*) FROM users WHERE email = :email", nativeQuery = true)
	long countByEmailIncludingDeleted(String email);

	default boolean existsByUsernameIncludingDeleted(String username) {
	    return countByUsernameIncludingDeleted(username) > 0;
	}

	default boolean existsByEmailIncludingDeleted(String email) {
	    return countByEmailIncludingDeleted(email) > 0;
	}


	
}
