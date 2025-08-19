package com.oranbyte.recipebook.seeder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oranbyte.recipebook.entity.Category;
import com.oranbyte.recipebook.repository.CategoryRepository;

@Component
public class CategorySeeder implements Seeder{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public void seed() {
		if (categoryRepository.count() == 0) {
            List<Category> categories = new ArrayList<>();

            categories.add(Category.builder().name("Appetizers").description("Starters to begin your meal").build());
            categories.add(Category.builder().name("Main Course").description("Hearty and fulfilling dishes").build());
            categories.add(Category.builder().name("Desserts").description("Sweet endings").build());
            categories.add(Category.builder().name("Salads").description("Healthy and fresh").build());
            categories.add(Category.builder().name("Beverages").description("Drinks and refreshments").build());
            categories.add(Category.builder().name("Breakfast").description("Morning meals to start the day").build());
            categories.add(Category.builder().name("Lunch").description("Midday dishes").build());
            categories.add(Category.builder().name("Dinner").description("Evening meals").build());
            categories.add(Category.builder().name("Snacks").description("Quick bites").build());
            categories.add(Category.builder().name("Soups").description("Warm and comforting").build());

            categoryRepository.saveAll(categories);
            System.out.println("✅ Seeded 10 categories.");
        }
	}

}
