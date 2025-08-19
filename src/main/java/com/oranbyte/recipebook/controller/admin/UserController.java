package com.oranbyte.recipebook.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.exception.ResourceNotFoundException;
import com.oranbyte.recipebook.service.PaginationService;
import com.oranbyte.recipebook.service.RecipeReactionService;
import com.oranbyte.recipebook.service.RecipeService;
import com.oranbyte.recipebook.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/settings/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PaginationService paginationService;
	
	@Autowired
	private RecipeReactionService recipeReactionService;
	
	@Autowired
	private RecipeService recipeService;

	@GetMapping
	public String index(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String search, Model model) {

		int pageIndex = Math.max(page - 1, 0);

		Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<UserDto> users = userService.searchUsers(search, pageable);
		
		users.getContent().forEach(user -> {
	        user.setLikeCount(recipeReactionService.getLikeCountByUserId(user.getId()));
	        user.setDislikeCount(recipeReactionService.getDislikeCountByUserId(user.getId()));
	        user.setRecipeCount(recipeService.getRecipeCount(user.getId()));
	    });

		model.addAttribute("users", users.getContent());
		model.addAllAttributes(paginationService.getPageMetadata(users, page));
		model.addAttribute("currentPageDisplay", page);

		return "settings/users-list";
	}
	
	@DeleteMapping("/{userId}")
	public String destroy(@PathVariable Long userId,
	                      RedirectAttributes redirectAttr,
	                      HttpServletRequest req) {
	    try {
	        User user = userService.getUser(userId);
	        if (user == null) {
	            throw new ResourceNotFoundException("User Not Found");
	        }
	        
	        User authUser = userService.getLoginUser();
	        boolean isOwner = user.getId().equals(user.getId());
	        boolean isAdmin = "admin".equals(authUser.getRole());

	        if (!isAdmin) {
	            redirectAttr.addFlashAttribute("error", "Unauthorized!");
	            return "redirect:" + req.getHeader("Referer");
	        }

	        userService.deleteUser(user.getId());
	        redirectAttr.addFlashAttribute("success", "User Deleted Successfully!");

	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttr.addFlashAttribute("error", e.getMessage());
	    }

	    return "redirect:" + req.getHeader("Referer");
	}

}
