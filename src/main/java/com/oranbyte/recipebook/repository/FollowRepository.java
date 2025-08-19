package com.oranbyte.recipebook.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.Follow;
import com.oranbyte.recipebook.entity.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Long countByFollowingAndCreatedAtAfter(User user, LocalDateTime date);

    Long countByFollowerAndCreatedAtAfter(User user, LocalDateTime date);

	void deleteByFollowerAndFollowing(User follower, User following);
}
