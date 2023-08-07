package com.omada5.ergasia_omadas_5.task;

import com.omada5.ergasia_omadas_5.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class TaskConfig {

    @Bean
    CommandLineRunner commandLineRunner3(ApplicationContext appContext,
                                         TaskRepository repository,
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

            Task task2 = new Task(
                    "This is a Title",
                    "This is a description",
                    true,
                    false,
                    true,
                    500,
                    24 * 60
            );

            Optional<Category> optionalCategory2 = categoryRepository.findCategoryByName("Android App");
            optionalCategory2.ifPresent(task2::setCategory);

            Task task3 = new Task(
                    "Web application",
                    "Spring boot web application for a fiverr like app. Spring Boot Extension is Spring's convention-over-configuration solution for creating production-grade Spring applications with minimal amounts of configuration.",
                    true,
                    true,
                    true,
                    1000,
                    48 * 60
            );

            Optional<Category> optionalCategory1 = categoryRepository.findCategoryByName("Web App");
            Optional<Subcategory> optionalSubcategory1 = subcategoryRepository.findSubcategoryByName("Spring Boot");
            if (optionalCategory1.isPresent() && optionalSubcategory1.isPresent()){
                task1.setCategory(optionalCategory1.get());
                task1.setSubcategory(optionalSubcategory1.get());
                task3.setCategory(optionalCategory1.get());
                task3.setSubcategory(optionalSubcategory1.get());
            }

            repository.saveAll(List.of(task1, task2, task3));
        };
    }
}
