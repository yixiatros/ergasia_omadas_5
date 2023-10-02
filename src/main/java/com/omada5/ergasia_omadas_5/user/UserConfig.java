package com.omada5.ergasia_omadas_5.user;

import com.omada5.ergasia_omadas_5.task.Task;
import com.omada5.ergasia_omadas_5.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.Month.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository,
                                        RoleRepository roleRepository,
                                        TaskRepository taskRepository,
                                        PasswordEncoder passwordEncoder){

        return args -> {
            Optional<Role> optionalDeveloperRole = roleRepository.findByName("developer");
            Optional<Role> optionalClientRole = roleRepository.findByName("client");
            Optional<Role> optionalAdminRole = roleRepository.findByName("admin");
            List<Task> tasks = taskRepository.findAll();
            List<User> users = new ArrayList<>();

            if (!repository.findByUsername("admin").isPresent()){
                User user = new User(
                        "admin",
                        "admin",
                        passwordEncoder.encode("admin"),
                        "admin@taskcode.gr",
                        LocalDate.of(2023, 9, 21)
                );
                optionalAdminRole.ifPresent(user::addRole);
                users.add(user);
            }

            if (!repository.findByUsername("mpampis21").isPresent()){
                User user1 = new User(
                        "Mpampis",
                        "mpampis21",
                        passwordEncoder.encode("aekara21"),
                        "mpampis21@gmail.com",
                        LocalDate.of(2000, AUGUST, 21)
                );
                optionalDeveloperRole.ifPresent(user1::addRole);
                users.add(user1);
            }

            if (!repository.findByUsername("giannis1").isPresent()) {
                User user2 = new User(
                        "Giannis",
                        "giannis1",
                        passwordEncoder.encode("password"),
                        "giannis.1@gmail.com",
                        LocalDate.of(1997, MARCH, 30)
                );
                optionalClientRole.ifPresent(user2::addRole);
                users.add(user2);
            }

            if (!repository.findByUsername("juanita").isPresent()) {
                User user3 = new User(
                        "juana",
                        "juanita",
                        passwordEncoder.encode("juanitaLindita"),
                        "juana.linda@gmail.com",
                        LocalDate.of(1995, DECEMBER, 17)
                );
                optionalClientRole.ifPresent(user3::addRole);
                users.add(user3);
            }

            repository.saveAll(users);

            if (tasks.get(0).getCreator() == null)
                repository.findUserById(4L).ifPresent(tasks.get(0)::setCreator);
            if (tasks.get(1).getCreator() == null)
                repository.findUserById(3L).ifPresent(tasks.get(1)::setCreator);
            if (tasks.get(2).getCreator() == null)
                repository.findUserById(3L).ifPresent(tasks.get(2)::setCreator);

            taskRepository.saveAll(tasks);
        };
    }
}
