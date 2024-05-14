package com.example.darts.configuration;

import com.example.darts.repository.AccountRepository;
import com.example.darts.service.DartsAccountDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.authorizeHttpRequests(
                // Define which urls are visible by which users
                authorizeRequests -> authorizeRequests
                        // All static resources which are situated in js, images, css are available for anyone
                        .requestMatchers("/js/**", "/css/**", "/img/**", "/fonts/**", "/scss/**").permitAll()
                        // Allow anyone to see the home page, the registration page and the login form
                        .requestMatchers("/", "/users/login", "/users/register").permitAll()
                        //.requestMatchers().hasRole(UserRoleEnum.ADMIN.name())
                        // all other requests are authenticated.
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> formLogin
                        // redirect here when we access something which is not allowed.
                        // also this is the page where we perform login.
                        .loginPage("/account/login")
                        // The names of the input fields (in our case in auth-login.html)
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home")
                        .failureUrl("/account/login?error")
        ).logout(
                logout -> logout
                        // the URL where we should POST something in order to perform the logout
                        .logoutUrl("/account/logout")
                        // where to go when logged out?
                        .logoutSuccessUrl("/")
                        // invalidate the HTTP session
                        .invalidateHttpSession(true)
        ).build();

    }

    @Bean
    public UserDetailsService userDetailsService(AccountRepository accountRepository) {
        // This service translates the my_movie_app users and roles
        // to representation which spring security understands.
        return new DartsAccountDetailsService(accountRepository);
    }
}
