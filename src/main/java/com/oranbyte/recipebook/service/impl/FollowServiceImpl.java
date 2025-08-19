package com.oranbyte.recipebook.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oranbyte.recipebook.entity.Follow;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.repository.FollowRepository;
import com.oranbyte.recipebook.service.FollowService;

@Service
public class FollowServiceImpl implements FollowService {

	@Autowired
	private FollowRepository followRepository;

	@Override
	@Transactional
	public void follow(User follower, User following) {
		if (follower.equals(following)) {
			throw new IllegalArgumentException("You cannot follow yourself");
		}
		if (!follower.isFollowing(following)) {
			Follow follow = Follow.builder().follower(follower).following(following)
					.build();
			followRepository.save(follow);
		}
	}

	@Override
	@Transactional
	public void unfollow(User follower, User following) {
		followRepository.deleteByFollowerAndFollowing(follower, following);
	}

	@Override
	public Long getWeeklyFollowers(User user) {
		return followRepository.countByFollowingAndCreatedAtAfter(user, LocalDateTime.now().minusWeeks(1));
	}

	@Override
	public Long getMonthlyFollowers(User user) {
		return followRepository.countByFollowingAndCreatedAtAfter(user, LocalDateTime.now().minusMonths(1));
	}

	@Override
	public Long getYearlyFollowers(User user) {
		return followRepository.countByFollowingAndCreatedAtAfter(user, LocalDateTime.now().minusYears(1));
	}

	@Override
	public Long getWeeklyFollowing(User user) {
		return followRepository.countByFollowerAndCreatedAtAfter(user, LocalDateTime.now().minusWeeks(1));
	}

	@Override
	public Long getMonthlyFollowing(User user) {
		return followRepository.countByFollowerAndCreatedAtAfter(user, LocalDateTime.now().minusMonths(1));
	}

	@Override
	public Long getYearlyFollowing(User user) {
		return followRepository.countByFollowerAndCreatedAtAfter(user, LocalDateTime.now().minusYears(1));
	}

}
