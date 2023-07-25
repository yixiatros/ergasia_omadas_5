package com.omada5.ergasia_omadas_5.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.Month.AUGUST;
import static java.time.Month.MARCH;

@Configuration
public class RoleConfig {
    @Bean
    CommandLineRunner commandLineRunner2(RoleRepository repository){
        Optional<Role> role = repository.findByName("client");
        if (role.isPresent())
            return null;

        return args -> {
            Role clientRole = new Role("client");
            Role developerRole = new Role("developer");
            Role adminRole = new Role("admin");

            repository.saveAll(
                    List.of(clientRole, developerRole, adminRole)
            );
        };
    }
}
