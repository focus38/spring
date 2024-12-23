package ru.focus.spring.auth.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(final HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(config -> config.jwt(jwt -> jwt.jwtAuthenticationConverter(
            createJwtAuthenticationConverter()
        )));
        http
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        return http.build();
    }

    private Converter<Jwt, JwtAuthenticationToken> createJwtAuthenticationConverter() {
        return jwt -> {
            final Collection<GrantedAuthority> authorities = new JwtGrantedAuthoritiesConverter().convert(jwt);
            final String userName = jwt.getClaimAsString("preferred_username");
            return new JwtAuthenticationToken(jwt, authorities, userName);
        };
    }
}
