package com.oranbyte.recipebook.controller.settings;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.request.ChangeUsernameRequest;
import com.oranbyte.recipebook.service.AuthService;
import com.oranbyte.recipebook.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/settings/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	@GetMapping
	public String index() {
		return "settings/account/account";
	}
	
	@PostMapping("/change-username")
	public String changeUsername(
	        @Valid @ModelAttribute ChangeUsernameRequest dto,
	        BindingResult bindingResult,
	        Principal principal,
	        HttpServletRequest req,
	        RedirectAttributes redirectAttr) {

	    if (bindingResult.hasErrors()) {
	        redirectAttr.addFlashAttribute("error",
	                bindingResult.getFieldError().getDefaultMessage());
	        return "redirect:" + req.getHeader("Referer");
	    }

	    if (userService.existsByUsername(dto.getUsername())) {
	        redirectAttr.addFlashAttribute("error", "This username is already taken.");
	        return "redirect:" + req.getHeader("Referer");
	    }

	    User user = userService.getUser(principal.getName());
	    user.setUsername(dto.getUsername());
	    userService.save(user);
	    authService.login(user);
	    
	    redirectAttr.addFlashAttribute("success", "Username changed successfully!");
	    return "redirect:" + req.getHeader("Referer");
	}

	@DeleteMapping("/delete")
	public String deleteUser(@RequestParam("keytext") String keytext,
	                         Principal principal,
	                         HttpServletRequest req,
	                         RedirectAttributes redirectAttr) {
	    try {
	        User user = userService.getUser(principal.getName());

	        String expected = "Delete " + user.getUsername();

	        if (!expected.equals(keytext.trim())) {
	            redirectAttr.addFlashAttribute("error", "Confirmation text does not match.");
	            return "redirect:" + req.getHeader("Referer");
	        }

	        userService.deleteUser(user.getId());

	        req.getSession().invalidate();

	        redirectAttr.addFlashAttribute("success", "Your account has been deleted permanently.");
	        return "redirect:/";
	    } catch (Exception e) {
	        redirectAttr.addFlashAttribute("error", "Failed to delete account: " + e.getMessage());
	        return "redirect:" + req.getHeader("Referer");
	    }
	}


}
