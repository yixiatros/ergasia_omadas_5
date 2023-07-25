package com.omada5.ergasia_omadas_5.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.time.Month.*;

@Configuration
public class UserConfig {
    @Autowired RoleRepository roleRepository;

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository){

        return args -> {
            User user1 = new User(
                    "Mpampis",
                    "mpampis21",
                    "aekara21",
                    "mpampis21@gmail.com",
                    LocalDate.of(2000, AUGUST, 21)
            );

            Optional<Role> optionalClientRole = roleRepository.findByName("client");
            if (optionalClientRole.isPresent())
                user1.addRole(optionalClientRole.get());

            User user2 = new User(
                    "Giannis",
                    "giannis1",
                    "password",
                    "giannis.1@gmail.com",
                    LocalDate.of(1997, MARCH, 30)
            );

            Optional<Role> optionalDeveloperRole = roleRepository.findByName("developer");
            if (optionalDeveloperRole.isPresent())
                user2.addRole(optionalDeveloperRole.get());

            repository.saveAll(
                    List.of(user1, user2)
            );
        };
    }
}
