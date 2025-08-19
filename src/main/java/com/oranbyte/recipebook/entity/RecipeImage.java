package com.oranbyte.recipebook.entity;

import java.io.File;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "recipe_image")
@SQLDelete(sql = "UPDATE recipe_image SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class RecipeImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath;

    private boolean isPrimary;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;
    
    public String getUrl() {
    	if (imagePath == null || imagePath.trim().isEmpty()) {
            return "/images/user.png";
        } else {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File file = new File(uploadDir + imagePath.trim());
            return file.exists() ? "/uploads/" + imagePath.trim() : "/images/avatars/default.png";
        }
    }
    
}
