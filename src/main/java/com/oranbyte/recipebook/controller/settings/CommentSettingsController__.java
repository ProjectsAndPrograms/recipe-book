package com.oranbyte.recipebook.controller.settings;

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

import com.oranbyte.recipebook.dto.CommentDto;
import com.oranbyte.recipebook.entity.Comment;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.exception.ResourceNotFoundException;
import com.oranbyte.recipebook.service.CommentService;
import com.oranbyte.recipebook.service.PaginationService;
import com.oranbyte.recipebook.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/settings/comments")
public class CommentSettingsController__ {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PaginationService paginationService;

	@GetMapping
	public String index(
			Model model,
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String search
		) {
		
		int pageIndex = Math.max(page - 1, 0);
		Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<CommentDto> commentPage = commentService.searchComments(search, search, pageable);
		
		model.addAttribute("comments", commentPage.getContent());
		model.addAllAttributes(paginationService.getPageMetadata(commentPage, page));
		model.addAttribute("currentPageDisplay", page);
		
		return "settings/comments/comment-list";
	}
	
	@DeleteMapping("/{commentId}")
	public String destroy(@PathVariable Long commentId,
	                      RedirectAttributes redirectAttr,
	                      HttpServletRequest req) {
	    try {
	        Comment comment = commentService.getComment(commentId);
	        if (comment == null) {
	            throw new ResourceNotFoundException("Not Found");
	        }

	        User user = userService.getLoginUser();
	        boolean isOwner = user.getId().equals(comment.getUser().getId());
	        boolean isAdmin = "admin".equalsIgnoreCase(user.getRole());

	        if (!isOwner && !isAdmin) {
	            redirectAttr.addFlashAttribute("error", "Unauthorized!");
	            return "redirect:" + req.getHeader("Referer");
	        }

	        commentService.deleteComment(comment.getId());
	        redirectAttr.addFlashAttribute("success", "Comment Deleted Successfully!");

	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttr.addFlashAttribute("error", e.getMessage());
	    }

	    return "redirect:" + req.getHeader("Referer");
	}
	
}
