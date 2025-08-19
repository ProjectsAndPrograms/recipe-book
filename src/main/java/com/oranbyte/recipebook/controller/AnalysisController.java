package com.oranbyte.recipebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.enums.ReactionType;
import com.oranbyte.recipebook.service.CommentService;
import com.oranbyte.recipebook.service.FollowService;
import com.oranbyte.recipebook.service.RecipeReactionService;
import com.oranbyte.recipebook.service.UserService;

@Controller
@RequestMapping("/settings/analysis")
public class AnalysisController {

	@Autowired
	private RecipeReactionService reactionService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private CommentService commentService;

	@GetMapping
	public String index(Model model) {

		User user = userService.getLoginUser();
		model.addAttribute("weeklyLike", reactionService.getWeeklyCount(user.getId(), ReactionType.LIKE));
		model.addAttribute("weeklyDislike", reactionService.getWeeklyCount(user.getId(), ReactionType.DISLIKE));

		model.addAttribute("monthlyLike", reactionService.getMonthlyCount(user.getId(), ReactionType.LIKE));
		model.addAttribute("monthlyDislike", reactionService.getMonthlyCount(user.getId(), ReactionType.DISLIKE));

		model.addAttribute("yearlyLike", reactionService.getYearlyCount(user.getId(), ReactionType.LIKE));
		model.addAttribute("yearlyDislike", reactionService.getYearlyCount(user.getId(), ReactionType.DISLIKE));

		model.addAttribute("weeklyFollowing", followService.getWeeklyFollowing(user));
		model.addAttribute("monthlyFollowing", followService.getMonthlyFollowing(user));
		model.addAttribute("yearlyFollowing", followService.getYearlyFollowing(user));
		
		model.addAttribute("weeklyFollowers", followService.getWeeklyFollowers(user));
		model.addAttribute("monthlyFollowers", followService.getMonthlyFollowers(user));
		model.addAttribute("yearlyFollowers", followService.getYearlyFollowers(user));

		model.addAttribute("weeklyComments", commentService.getWeeklyComments(user.getId()));
		model.addAttribute("monthlyComments", commentService.getMonthlyComments(user.getId()));
		model.addAttribute("yearlyComments", commentService.getYearlyComments(user.getId()));
		
		return "settings/analysis/analysis";
	}

}
