package com.omada5.ergasia_omadas_5.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class TaskConfig {

    @Bean
    CommandLineRunner commandLineRunner3(TaskRepository repository,
                                         CategoryRepository categoryRepository,
                                         SubcategoryRepository subcategoryRepository){
        return args -> {
            Task task1 = new Task(
                    "Title",
                    "Description",
                    true,
                    true,
                    false,
                    200,
                    5 * 60
            );

            Optional<Category> optionalCategory1 = categoryRepository.findCategoryByName("web application");
            Optional<Subcategory> optionalSubcategory1 = subcategoryRepository.findSubcategoryByName("spring boot application");
            if (optionalCategory1.isPresent() && optionalSubcategory1.isPresent()){
                task1.setCategory(optionalCategory1.get());
                task1.setSubcategory(optionalSubcategory1.get());
            }

            Task task2 = new Task(
                    "This is a Title",
                    "This is a description",
                    true,
                    false,
                    true,
                    500,
                    24 * 60
            );

            Optional<Category> optionalCategory2 = categoryRepository.findCategoryByName("android application");
            if (optionalCategory2.isPresent())
                task2.setCategory(optionalCategory2.get());

            repository.saveAll(List.of(task1, task2));
        };
    }
}
