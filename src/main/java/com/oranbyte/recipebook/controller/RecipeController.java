package com.oranbyte.recipebook.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oranbyte.recipebook.dto.CategoryDto;
import com.oranbyte.recipebook.dto.CommentDto;
import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.dto.RecipeIngredientDto;
import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.Tag;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.enums.ReactionType;
import com.oranbyte.recipebook.mapper.RecipeIngredientMapper;
import com.oranbyte.recipebook.mapper.RecipeMapper;
import com.oranbyte.recipebook.mapper.UserMapper;
import com.oranbyte.recipebook.service.CategoryService;
import com.oranbyte.recipebook.service.IngredientService;
import com.oranbyte.recipebook.service.PaginationService;
import com.oranbyte.recipebook.service.RecipeImageService;
import com.oranbyte.recipebook.service.RecipeReactionService;
import com.oranbyte.recipebook.service.RecipeService;
import com.oranbyte.recipebook.service.TagService;
import com.oranbyte.recipebook.service.UnitService;
import com.oranbyte.recipebook.service.UserDetailService;
import com.oranbyte.recipebook.service.UserService;
import com.oranbyte.recipebook.service.UserSocialLinksService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private RecipeImageService recipeImageService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private TagService tagService;

	@Autowired
	private IngredientService ingredientService;

	@Autowired
	private UnitService unitService;

	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private UserSocialLinksService userSocialLinksService;
	
	@Autowired
	private PaginationService paginationService;
	
	@Autowired
	private RecipeReactionService recipeReactionService;

	@GetMapping
	public String index(
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "15") int size,
	        @RequestParam(required = false) Long categoryId,
	        @RequestParam(required = false) Long tagId,
	        @RequestParam(required = false) String title,
	        @RequestParam(required = false) String difficulty,
	        Model model) {

	    int pageIndex = Math.max(page - 1, 0);

	    Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.DESC, "createdAt"));
	    Page<RecipeDto> recipePage = recipeService.searchRecipes(categoryId, tagId, title, difficulty, pageable);

	    model.addAttribute("recipes", recipePage.getContent());
	    model.addAllAttributes(paginationService.getPageMetadata(recipePage, page));
	    model.addAttribute("currentPageDisplay", page);
	    
	    List<CategoryDto> categories = categoryService.getAllCategories();
	    CategoryDto currentCategory = categories.stream()
	            .filter(category -> category.getId().equals(categoryId))
	            .findFirst()
	            .orElse(null);

	    model.addAttribute("categoryId", categoryId);
	    model.addAttribute("category", currentCategory);
	    model.addAttribute("categories", categories);

	    return "recipes/recipes";
	}

	@GetMapping("create")
	public String create(Model model) {
		model.addAttribute("units", unitService.getAll());
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("recipeDto", new RecipeDto());

		return "recipes/create-recipe";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute RecipeDto recipeDto,
			@RequestParam(value = "images", required = false) MultipartFile[] images,
			@RequestParam(value = "tags", required = false) String[] tags,
			@RequestParam(value = "ingredient_names", required = false) String[] ingredientNames,
			@RequestParam(value = "ingredient_quantities", required = false) Integer[] ingredientQuantities,
			@RequestParam(value = "ingredient_units", required = false) Long[] ingredientUnitIds,
			@RequestParam(value = "ingredient_notes", required = false) String[] ingredientNotes,
			RedirectAttributes redirectAttr, Principal principal, HttpServletRequest request) {

		try {
			User user = userService.getUser(principal.getName());
			Recipe recipe = recipeService.convertToEntity(recipeDto, user);

			if (tags.length > 0) {
				Set<Tag> savedTags = tagService.saveAll(tags);
				recipe.setTags(savedTags);
			}
			recipe.setUser(user);
			Recipe savedRecipe = recipeService.saveRecipe(recipe);

			if (ingredientNames.length > 0) {
				ingredientService.saveRecipeIngredients(savedRecipe, ingredientNames, ingredientQuantities,
						ingredientUnitIds, ingredientNotes);
			}

			if (images.length > 0) {
				recipeImageService.saveAll(savedRecipe, images);
			}

			redirectAttr.addFlashAttribute("success", "Your recipe created successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttr.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:" + request.getHeader("Referer");
	}

	@GetMapping("/{recipe}")
	public String show(@PathVariable Recipe recipe, Model model) {
		
		long likeCount = recipeReactionService.getLikeCount(recipe);
		User recipeUser = recipe.getUser();
		model.addAttribute("recipe", RecipeMapper.toDto(recipe));
		model.addAttribute("ingredients", ingredientService.getIngredients(recipe.getId()));
		
		model.addAttribute("user", UserMapper.toDto(recipeUser));
		model.addAttribute("comment_dto", CommentDto.builder().recipeId(recipe.getId()).build());
		model.addAttribute("user_detail", userDetailService.getUserDetailDto(recipe.getUser().getId()));
		model.addAttribute("user_social_links", userSocialLinksService.getUserSocialLinks(recipe.getUser().getId()));
		model.addAttribute("authors", userService.getThreeRandomUsersWithRecipes());

		model.addAttribute("tags", recipe.getTags());
		model.addAttribute("recent_recipies", recipeService.recentRecipes(PageRequest.of(0, 3)));
		
		User loginUser = userService.getLoginUser();
		boolean isLiked = recipeReactionService.isReacted(loginUser, recipe, ReactionType.LIKE);
		boolean isDisliked = recipeReactionService.isReacted(loginUser, recipe, ReactionType.DISLIKE);

		model.addAttribute("isLiked", isLiked);
		model.addAttribute("likeCount", likeCount == 0 ? "Like" : likeCount);
		model.addAttribute("isDisliked", isDisliked);
		
		model.addAttribute("isFollowing", recipeUser.isFollowedBy(loginUser));
		
		model.addAttribute("categories", categoryService.getAllCategories());
		return "recipes/recipe-details";
	}
	
	@DeleteMapping("{recipe}/delete")
	public String destroy(@PathVariable Recipe recipe, Model model, RedirectAttributes redirectAttr, HttpServletRequest request) {
		try {
			User user = userService.getLoginUser();
			 if (user != null && (user.getId().equals(recipe.getUser().getId()) || user.getRole().equalsIgnoreCase("admin"))) {
					recipeService.deleteRecipe(recipe);
					redirectAttr.addFlashAttribute("success", "Recipe Delete Successfully!");
			 }else {
				 redirectAttr.addFlashAttribute("error", "Unauthorized!");
			 }
			
			
		}catch(Exception e) {
			redirectAttr.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:" + request.getHeader("Referer");
	}
	
	@GetMapping("/{recipe}/edit")
	public String edit(@PathVariable Recipe recipe, Model model) {
		model.addAttribute("units", unitService.getAll());
		model.addAttribute("categories", categoryService.getAllCategories());
		
		RecipeDto dto = RecipeMapper.toDto(recipe);
		
		Set<Tag> tags = recipe.getTags();
		List<RecipeIngredientDto> ingredients = recipe.getIngredients().stream().map(RecipeIngredientMapper::toDto).toList();
		
		model.addAttribute("recipeDto", dto);
		model.addAttribute("tags", tags);
		model.addAttribute("ingredients", ingredients);
		return "recipes/edit-recipe";
	}
	
	@PostMapping("/{id}/update")
	public String update(@PathVariable Long id,
	        @ModelAttribute RecipeDto recipeDto,
	        @RequestParam(value = "images", required = false) MultipartFile[] images,
	        @RequestParam(value = "tags", required = false) String[] tags,
	        @RequestParam(value = "ingredient_names", required = false) String[] ingredientNames,
	        @RequestParam(value = "ingredient_quantities", required = false) Integer[] ingredientQuantities,
	        @RequestParam(value = "ingredient_units", required = false) Long[] ingredientUnitIds,
	        @RequestParam(value = "ingredient_notes", required = false) String[] ingredientNotes,
	        RedirectAttributes redirectAttr, Principal principal, HttpServletRequest request) {

	    try {
	        User user = userService.getUser(principal.getName());
	        Recipe existingRecipe = recipeService.getRecipe(id)
	                .orElseThrow(() -> new RuntimeException("Recipe not found!"));

	        if(!user.getRole().equals("admin") && user.getId() != existingRecipe.getUser().getId()) {
	        	throw new RuntimeException("Access Denied!");
	        }
	        
	        recipeService.updateEntityFromDto(existingRecipe, recipeDto, user);

	        if (tags != null && tags.length > 0) {
	            Set<Tag> savedTags = tagService.saveAll(tags);
	            existingRecipe.setTags(savedTags);
	        } else {
	            existingRecipe.setTags(Collections.emptySet());
	        }

	        ingredientService.deleteByRecipe(existingRecipe);
	        if (ingredientNames != null && ingredientNames.length > 0) {
	            ingredientService.saveRecipeIngredients(existingRecipe, ingredientNames, ingredientQuantities,
	                    ingredientUnitIds, ingredientNotes);
	        }

	        if (images != null && images.length > 0) {
	            recipeImageService.saveAll(existingRecipe, images);
	        }

	        recipeService.saveRecipe(existingRecipe);

	        redirectAttr.addFlashAttribute("success", "Your recipe updated successfully!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttr.addFlashAttribute("error", e.getMessage());
	    }

	    return "redirect:" + request.getHeader("Referer");
	}


}
