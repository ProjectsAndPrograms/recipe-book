package com.oranbyte.recipebook.service;

import com.oranbyte.recipebook.entity.User;

public interface FollowService {

	void follow(User follower, User following);
	
	void unfollow(User follower, User following);
	
	Long getWeeklyFollowers(User user);
	
	Long getMonthlyFollowers(User user);
	
	Long getYearlyFollowers(User user);
	
	Long getWeeklyFollowing(User user);
	
	Long getMonthlyFollowing(User user);
	
	Long getYearlyFollowing(User user);
	
	
}
