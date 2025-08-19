package com.oranbyte.recipebook.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.mapper.UserMapper;
import com.oranbyte.recipebook.repository.UserRepository;
import com.oranbyte.recipebook.service.UserService;
import com.oranbyte.recipebook.specification.UserSpecification;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto getUserDto(Long id) {
		return userRepository.findById(id).map(UserMapper::toDto).orElse(null);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<UserDto> getThreeRandomUsersWithRecipes() {
		return userRepository.findThreeRandomUsersWithRecipes().stream().map(UserMapper::toDto).toList();
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public boolean existsByEmailIncludingDeleted(String email) {
		return userRepository.existsByEmailIncludingDeleted(email);
	}

	@Override
	public boolean existsByUsernameIncludingDeleted(String username) {
		return userRepository.existsByUsernameIncludingDeleted(username);
	}

	@Override
	public User getUser(String username) {
		return userRepository.findByUsernameOrEmail(username, username).orElse(null);
	}

	@Override
	public UserDto getUserDto(String username) {
		return userRepository.findByUsername(username).map(UserMapper::toDto).orElse(null);
	}

	@Override
	public User getLoginUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		System.out.println(auth);
		if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
			return null;
		}

		Object principal = auth.getPrincipal();
		String username;
		System.out.println(principal);
		if (principal instanceof UserDetails userDetails) {
			username = userDetails.getUsername();
		} else {
			username = principal.toString();
		}
		System.out.println(username);
		return getUser(username);
	}

	@Override
	public User getUser(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public Page<UserDto> searchUsers(String search, Pageable pageable) {
		Specification<User> spec = UserSpecification.hasNameLike(search).and(UserSpecification.hasUsernameLike(search))
				.and(UserSpecification.hasEmailLike(search));

		return userRepository.findAll(spec, pageable).map(UserMapper::toDto);
	}

	@Override
	public boolean isAdmin(String userName) {
		return userRepository.existsByUsernameAndRole(userName, "admin");
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public void updatePassword(String email, String newPassword) {
		userRepository.findByEmail(email).ifPresent(user -> {
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
		});
	}

}
