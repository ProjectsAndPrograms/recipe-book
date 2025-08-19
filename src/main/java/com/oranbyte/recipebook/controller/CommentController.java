package com.oranbyte.recipebook.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.oranbyte.recipebook.dto.CommentDto;
import com.oranbyte.recipebook.entity.Comment;
import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.mapper.CommentMapper;
import com.oranbyte.recipebook.repository.CommentRepository;
import com.oranbyte.recipebook.repository.RecipeRepository;
import com.oranbyte.recipebook.service.CommentService;
import com.oranbyte.recipebook.service.UserService;
import com.oranbyte.recipebook.util.DateUtil;

@RestController
@RequestMapping("/recipe")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private CommentRepository commentRepository;

	@PostMapping({ "/comment", "/comment/{parentId}" })
	public ResponseEntity<Map<String, Object>> save(@PathVariable(name = "parentId", required = false) Long parentId,
			@ModelAttribute CommentDto dto, Principal principal) {

		Map<String, Object> response = new HashMap<>();

		if (principal == null) {
			response.put("status", "error");
			response.put("message", "Unauthorized!");
			return ResponseEntity.badRequest().body(response);
		}

		try {

			User user = userService.getUser(principal.getName());
			System.out.println(dto.getRecipeId());
			Recipe recipe = recipeRepository.findById(dto.getRecipeId())
					.orElseThrow(() -> new RuntimeException("Recipe not found"));

			Comment.CommentBuilder commentBuilder = Comment.builder().message(dto.getMessage()).recipe(recipe)
					.user(user);

			if (parentId != null) {
				Comment parentComment = commentRepository.findById(parentId)
						.orElseThrow(() -> new RuntimeException("Parent comment not found"));
				commentBuilder.parent(parentComment);
			}

			Comment comment = commentService.save(commentBuilder.build());

			response.put("status", "success");
			response.put("message", "Comment created successfully!");
			
			Context context = new Context();
			context.setVariable("reply", CommentMapper.toShallowDto(comment));

			String html = templateEngine.process(
				    "recipes/partials/reply-comment",
				    context
				);
			
			response.put("html", html);
			response.put("comment", Map.of("userName", user.getUsername(), "imagePath", user.getImageUrl(), "message",
					comment.getMessage(), "timeAgo", DateUtil.timeAgo(comment.getCreatedAt())));

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/comments")
	public ResponseEntity<Map<String, Object>> getComments(@RequestParam Long recipeId, @RequestParam int page) {

		Pageable pageable = PageRequest.of(page, 3, Sort.by("createdAt").descending());

		Page<Comment> commentPage = commentRepository.findByRecipeIdAndParentIsNull(recipeId, pageable);

		List<CommentDto> commentDtos = commentPage.getContent().stream().map(CommentMapper::toDto).toList();

		Context context = new Context();
		context.setVariable("comments", commentDtos);
		context.setVariable("authUser", userService.getLoginUser());

		String html = templateEngine.process("recipes/comments-list", context);

		Map<String, Object> response = new HashMap<>();
		response.put("html", html);
		response.put("isLastPage", commentPage.isLast());

		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/comments/{commentId}")
	public ResponseEntity<Map<String, Object>> getComment(@PathVariable Long commentId) {
		
		Comment comment = commentRepository.findById(commentId).orElse(null);
		
		CommentDto commentDto = CommentMapper.toDto(comment);
		
		Context context = new Context();
		context.setVariable("comment", commentDto);
		context.setVariable("authUser", userService.getLoginUser());
		
		String html = templateEngine.process("recipes/partials/comment", context);
		
		Map<String, Object> response = new HashMap<>();
		response.put("html", html);
		
		return ResponseEntity.ok(response);
	}

	@GetMapping("/comment/replies")
	public ResponseEntity<Map<String, Object>> getReplies(@RequestParam Long commentId, @RequestParam int page) {
		Pageable pageable = PageRequest.of(page > 0 ? page - 1: page, 10, Sort.by("createdAt").descending());

		Page<Comment> replyPage = commentRepository.findByParentId(commentId, pageable);

		List<CommentDto> replyDtos = replyPage.getContent().stream().map(CommentMapper::toShallowDto).toList();

		Context context = new Context();
		context.setVariable("replies", replyDtos);

		String html = templateEngine.process(
			    "recipes/partials/replies-list",
			    context
			);
		Map<String, Object> response = new HashMap<>();
		response.put("html", html);
		response.put("isLastPage", replyPage.isLast());

		return ResponseEntity.ok(response);
	}
	
	


}
