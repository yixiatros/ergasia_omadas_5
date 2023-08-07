package com.omada5.ergasia_omadas_5.user;

import com.omada5.ergasia_omadas_5.task.Task;
import com.omada5.ergasia_omadas_5.task.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.Month.*;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository,
                                        RoleRepository roleRepository,
                                        TaskRepository taskRepository){

        return args -> {
            User user1 = new User(
                    "Mpampis",
                    "mpampis21",
                    "aekara21",
                    "mpampis21@gmail.com",
                    LocalDate.of(2000, AUGUST, 21)
            );

            Optional<Role> optionalDeveloperRole = roleRepository.findByName("developer");
            optionalDeveloperRole.ifPresent(user1::addRole);

            User user2 = new User(
                    "Giannis",
                    "giannis1",
                    "password",
                    "giannis.1@gmail.com",
                    LocalDate.of(1997, MARCH, 30)
            );

            Optional<Role> optionalClientRole = roleRepository.findByName("client");
            optionalClientRole.ifPresent(user2::addRole);

            User user3 = new User(
                    "juana",
                    "juanita",
                    "juanitaLindita",
                    "juana.linda@gmail.com",
                    LocalDate.of(1995, DECEMBER, 17)
            );
            optionalDeveloperRole.ifPresent(user3::addRole);

            List<Task> tasks = taskRepository.findAll();
            tasks.get(0).setCreator(user3);
            tasks.get(1).setCreator(user1);
            tasks.get(2).setCreator(user2);
            taskRepository.saveAll(tasks);

            repository.saveAll(List.of(user1, user2, user3));
        };
    }
}
