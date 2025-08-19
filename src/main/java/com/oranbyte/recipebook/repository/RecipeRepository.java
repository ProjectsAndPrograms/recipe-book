package com.oranbyte.recipebook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.Category;
import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.User;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

	List<Recipe> findTop20ByOrderByIdDesc();

	Page<Recipe> findByOrderByIdDesc(Pageable pageable);

	long countByCategory(Category category);

	@Query("SELECT COUNT(r) FROM Recipe r WHERE r.user.id = :userId")
	int countRecipesByUserId(@Param("userId") Long userId);

	Optional<Recipe> findByIdAndUser(Long id, User user);

	@Query(value = "SELECT * FROM recipe WHERE deleted_at IS NULL ORDER BY RAND() LIMIT 1", nativeQuery = true)
	Recipe findRandomRecipe();

	@Query("""
			SELECT r
			FROM Recipe r
			LEFT JOIN r.reactions rr
			WHERE rr.reactionType = com.oranbyte.recipebook.enums.ReactionType.LIKE
			GROUP BY r
			ORDER BY COUNT(rr) DESC
			LIMIT 1
			""")
	Recipe findMostPopularRecipe();

	@Query("""
			    SELECT r
			    FROM Recipe r
			    LEFT JOIN r.reactions rr
			    WHERE rr.reactionType = com.oranbyte.recipebook.enums.ReactionType.LIKE
			      AND FUNCTION('YEAR', r.createdAt) = FUNCTION('YEAR', CURRENT_DATE)
			      AND FUNCTION('MONTH', r.createdAt) = FUNCTION('MONTH', CURRENT_DATE)
			    GROUP BY r
			    ORDER BY COUNT(rr) DESC
			""")
	List<Recipe> findMostPopularRecipesThisMonth(Pageable pageable);

}
