package com.oranbyte.recipebook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration including {@link PasswordEncoder} and {@link SecurityFilterChain}
 * provides role based authentication configuration, static resource access(CSS, JS), logout configuration
 * 
 * @author Shubham kumar
 * @since 1.0
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	
	@Autowired
	private UserDetailsService userDetailsService;

	
	/**
	 * Create an password encoder object internally uses {@link BCryptPasswordEncoder}
	 * 
	 * @return {@link PasswordEncoder} object
	 */
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * It defines all the authorization routes and their corresponding roles, static resources, login & logout settings
	 * 
	 * @return {@link SecurityFilterChain} object
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(Customizer.withDefaults())
					.authorizeHttpRequests(authorize -> authorize
						    .requestMatchers("/admin/**").hasRole("admin")
						    .requestMatchers("/user/**").hasAnyRole("user", "admin")
						    .requestMatchers("/settings/**").hasAnyRole("user", "admin")
						    .requestMatchers("/login", "/register", "/signup", "/css/**", "/js/**",  "/images/**", "/plugins/**", "/**").permitAll()
						    .anyRequest().authenticated()
						)
					.formLogin(form -> form
								.loginPage("/login")
								.loginProcessingUrl("/login")
								.usernameParameter("username")
								.passwordParameter("password")
								.defaultSuccessUrl("/", true)
								.permitAll())
					.logout(logout -> logout
							.logoutSuccessUrl("/login?logout")
							.permitAll()
							);

		
		return http.build();
	}
	
	
	/** 
	 * sets the generated com.loqshop.config.WebSecurityConfig#passwordEncoder() to {@link UserDetailsService}
	 * 
	 * @throws Exception - If the {@link AuthenticationManagerBuilder} or {@link UserDetailsService} is null
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	
	
	
}
