package com.omada5.ergasia_omadas_5.task;

import com.omada5.ergasia_omadas_5.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
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
                    LocalDateTime.of(2023, 11, 3, 20, 30)
            );
            task1.setAvailableLanguage(AvailableLanguages.English);
            task1.setProposedTechnology(ProposedTechnologies.HTML);

            Task task2 = new Task(
                    "This is a Title",
                    "This is a description",
                    true,
                    false,
                    true,
                    500,
                    LocalDateTime.of(2023, 12, 20, 14, 0)
            );

            Optional<Category> optionalCategory2 = categoryRepository.findCategoryByName("Android App");
            optionalCategory2.ifPresent(task2::setCategory);
            task2.setAvailableLanguage(AvailableLanguages.English);
            task2.setProposedTechnology(ProposedTechnologies.JAVA);

            Task task3 = new Task(
                    "Web application",
                    "Spring boot web application for a fiverr like app. Spring Boot Extension is Spring's convention-over-configuration solution for creating production-grade Spring applications with minimal amounts of configuration.",
                    true,
                    true,
                    true,
                    1000,
                    LocalDateTime.of(2023, 12, 28, 12, 40)
            );
            task3.setAvailableLanguage(AvailableLanguages.English);
            task3.setProposedTechnology(ProposedTechnologies.JAVA);

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
