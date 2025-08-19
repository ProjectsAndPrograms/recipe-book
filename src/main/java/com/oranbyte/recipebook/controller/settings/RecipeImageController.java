package com.oranbyte.recipebook.controller.settings;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oranbyte.recipebook.entity.RecipeImage;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.service.RecipeImageService;
import com.oranbyte.recipebook.service.UserService;

@Controller
@RequestMapping("/recipe-images")
public class RecipeImageController {

	@Autowired
	private RecipeImageService recipeImageService;
	
	@Autowired
	private UserService userService;
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
	    Map<String, String> map = new HashMap<>();
	    try {
	        User user = userService.getLoginUser();
	        RecipeImage recipeImage = recipeImageService.getRecipeImage(id)
	            .orElseThrow(() -> new IllegalArgumentException("Image not found"));

	        if (user != null && (user.getId().equals(recipeImage.getRecipe().getUser().getId()) || user.getRole().equalsIgnoreCase("admin"))) {
	            recipeImageService.deleteRecipeImage(recipeImage);
	            map.put("status", "success");
	            map.put("message", "Image deleted successfully!");
	            return ResponseEntity.ok(map);
	        } else {
	        	map.put("status", "error");
	            map.put("message", "Unauthorized");
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
	        }
	    } catch (Exception e) {
	    	map.put("status", "error");
	        map.put("message", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
	    }
	}

	
}
