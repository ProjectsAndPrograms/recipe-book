package com.oranbyte.recipebook.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;


public interface UserService {
	
	User getUser(String username);
	
	User getUser(Long id);

	UserDto getUserDto(String username);
	
	UserDto getUserDto(Long id);
	
	User save(User user);
	
	List<UserDto> getThreeRandomUsersWithRecipes();
	
	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
	
	boolean existsByEmailIncludingDeleted(String email);
	
	boolean existsByUsernameIncludingDeleted(String username);
	
	boolean isAdmin(String userName);
	
	User getLoginUser();

	Page<UserDto> searchUsers(String search, Pageable pageable);
	
	void deleteUser(Long userId);
	
	 void updatePassword(String email, String newPassword);
}
