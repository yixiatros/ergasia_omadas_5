package com.omada5.ergasia_omadas_5.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.Month.AUGUST;
import static java.time.Month.MARCH;

@Configuration
public class RoleConfig {
    @Bean
    CommandLineRunner commandLineRunner2(RoleRepository repository){
        return args -> {
            List<Role> roles = new ArrayList<>();

            Optional<Role> roleOptional = repository.findByName("client");
            if (!roleOptional.isPresent()){
                Role clientRole = new Role("client");
                roles.add(clientRole);
            }

            roleOptional = repository.findByName("developer");
            if (!roleOptional.isPresent()){
                Role developerRole = new Role("developer");
                roles.add(developerRole);
            }

            roleOptional = repository.findByName("admin");
            if (!roleOptional.isPresent()){
                Role adminRole = new Role("admin");
                roles.add(adminRole);
            }

            repository.saveAll(roles);
        };
    }
}
