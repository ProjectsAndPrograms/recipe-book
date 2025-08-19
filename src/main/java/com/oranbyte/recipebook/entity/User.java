package com.oranbyte.recipebook.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	private String image;

	private String role;

	public String getImageUrl() {
		if (image == null || image.trim().isEmpty()) {
			return "/images/john-doe.jpg";
		} else {
			String uploadDir = System.getProperty("user.dir") + "/uploads/";
			File file = new File(uploadDir + image.trim());
			return file.exists() ? "/uploads/" + image.trim() : "/images/john-doe.jpg";
		}
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Recipe> recipes = new ArrayList<>();

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserDetail userDetail;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserSocialLink> socialLinks = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RecipeReaction> reactions = new ArrayList<>();

	@OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Follow> following = new HashSet<>();

	@OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Follow> followers = new HashSet<>();


	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	public boolean isFollowing(User user) {
	    return this.following.stream()
	            .anyMatch(f -> f.getFollowing().equals(user));
	}

	public boolean isFollowedBy(User user) {
	    return this.followers.stream()
	            .anyMatch(f -> f.getFollower().equals(user));
	}

	
}
