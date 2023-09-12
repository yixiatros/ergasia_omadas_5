package com.omada5.ergasia_omadas_5.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class CategoryConfig {

    @Bean
    CommandLineRunner commandLineRunner4(CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository){
        return args -> {
            //Optional<Category> categoryOptional = categoryRepository.findCategoryByName("Andriod App");
            //if (categoryOptional.isPresent())

            Category category1 = new Category("Android App");
            Category category2 = new Category("Web App");

            Subcategory subcategory1 = new Subcategory("Spring Boot", category2);

            categoryRepository.saveAll(
                    List.of(category1, category2)
            );

            subcategoryRepository.save(subcategory1);
        };
    }
}
