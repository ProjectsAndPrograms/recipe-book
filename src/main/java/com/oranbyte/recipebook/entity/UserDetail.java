package com.oranbyte.recipebook.entity;

import java.io.File;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE user_detail SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class UserDetail extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String bio;

	private String websiteUrl;

	private String bannerImage;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	public String getBannerImageUrl() {
		if (bannerImage == null || bannerImage.trim().isEmpty()) {
			return null;
		} else {
			String uploadDir = System.getProperty("user.dir") + "/uploads/";
			File file = new File(uploadDir + bannerImage.trim());
			return file.exists() ? "/uploads/" + bannerImage.trim() : "/images/banner/default-banner.jpg";
		}
	}
}
