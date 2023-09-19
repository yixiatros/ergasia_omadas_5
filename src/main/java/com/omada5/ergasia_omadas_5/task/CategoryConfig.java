package com.omada5.ergasia_omadas_5.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class CategoryConfig {

    @Bean
    CommandLineRunner commandLineRunner4(CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository){
        return args -> {
            List<Category> categories = new ArrayList<>();
            List<Subcategory> subcategories = new ArrayList<>();

            Optional<Category> categoryOptional = categoryRepository.findCategoryByName("Android App");
            if (!categoryOptional.isPresent()) {
                Category category = new Category("Android App");
                categories.add(category);
            }
            categoryOptional = categoryRepository.findCategoryByName("Web App");
            if (!categoryOptional.isPresent()) {
                Category category = new Category("Web App");
                categories.add(category);
            }

            categoryRepository.saveAll(categories);

            Optional<Subcategory> subcategoryOptional = subcategoryRepository.findSubcategoryByName("Spring Boot");
            if (!subcategoryOptional.isPresent()){
                Subcategory subcategory = new Subcategory("Spring Boot", categoryRepository.findCategoryByName("Web App").get());
                subcategories.add(subcategory);
            }
            subcategoryOptional = subcategoryRepository.findSubcategoryByName("Portal");
            if (!subcategoryOptional.isPresent()){
                Subcategory subcategory = new Subcategory("Portal", categoryRepository.findCategoryByName("Web App").get());
                subcategories.add(subcategory);
            }
            subcategoryOptional = subcategoryRepository.findSubcategoryByName("Corporate Site");
            if (!subcategoryOptional.isPresent()){
                Subcategory subcategory = new Subcategory("Corporate Site", categoryRepository.findCategoryByName("Web App").get());
                subcategories.add(subcategory);
            }
            subcategoryOptional = subcategoryRepository.findSubcategoryByName("Educational");
            if (!subcategoryOptional.isPresent()){
                Subcategory subcategory = new Subcategory("Educational", categoryRepository.findCategoryByName("Android App").get());
                subcategories.add(subcategory);
            }
            subcategoryOptional = subcategoryRepository.findSubcategoryByName("Social Media");
            if (!subcategoryOptional.isPresent()){
                Subcategory subcategory = new Subcategory("Social Media", categoryRepository.findCategoryByName("Android App").get());
                subcategories.add(subcategory);
            }
            subcategoryOptional = subcategoryRepository.findSubcategoryByName("Video Game");
            if (!subcategoryOptional.isPresent()){
                Subcategory subcategory = new Subcategory("Video Game", categoryRepository.findCategoryByName("Android App").get());
                subcategories.add(subcategory);
            }

            subcategoryRepository.saveAll(subcategories);
        };
    }
}
