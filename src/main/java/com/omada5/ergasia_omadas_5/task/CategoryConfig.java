package com.omada5.ergasia_omadas_5.task;

import com.omada5.ergasia_omadas_5.user.RoleRepository;
import com.omada5.ergasia_omadas_5.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CategoryConfig {

    @Bean
    CommandLineRunner commandLineRunner4(CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository){
        return args -> {
            Category category1 = new Category("android application");
            Category category2 = new Category("web application");

            Subcategory subcategory1 = new Subcategory("spring boot application", category2);

            categoryRepository.saveAll(
                    List.of(category1, category2)
            );

            subcategoryRepository.save(subcategory1);
        };
    }
}
