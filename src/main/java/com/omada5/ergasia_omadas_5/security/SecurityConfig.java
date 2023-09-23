package com.omada5.ergasia_omadas_5.security;

import com.omada5.ergasia_omadas_5.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final TokenRepository tokenRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/vendor/**", "/fonts/**").permitAll()
                            .requestMatchers("/index", "/users/profile_view/**", "/users/user_search/**", "/task_search/**", "/tasks").permitAll()
                            .requestMatchers("/users/logout").hasAnyAuthority("client", "developer", "admin")
                            .requestMatchers("/users/**").hasAuthority("ROLE_ANONYMOUS") // restrict access to login/register page to loggedIn users
                            .requestMatchers("/task_create").hasAuthority("client")
                            .anyRequest().authenticated();
                })
                .sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(login -> {
                    login.loginPage("/users/loginToAccess");
                    login.failureUrl("/index");
                    login.failureForwardUrl("/index");
                })
                .logout(logout -> {
                    logout.logoutSuccessUrl("/index")
                            .addLogoutHandler(logoutHandler)
                            .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
                });

        return httpSecurity.build();
    }
}
