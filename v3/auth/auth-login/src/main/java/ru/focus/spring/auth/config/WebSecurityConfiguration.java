package ru.focus.spring.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(final HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(Customizer.withDefaults())
            .oauth2Login(Customizer.withDefaults())
            .oauth2Client(Customizer.withDefaults());
        http
            .authorizeHttpRequests(authorize ->
                authorize
                    .requestMatchers("/assets/**").permitAll()
                    .requestMatchers("/error").permitAll()
                    .anyRequest().authenticated()
            );
        return http.build();
    }
}
