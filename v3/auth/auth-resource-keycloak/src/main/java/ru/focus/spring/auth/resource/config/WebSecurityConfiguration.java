package ru.focus.spring.auth.resource.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.keycloak.util.SystemPropertiesJsonParserFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(final HttpSecurity http,
                                                          final PolicyEnforcerConfig policyEnforcerConfig) throws Exception {
        http.oauth2ResourceServer(config -> config.jwt(jwt -> jwt.jwtAuthenticationConverter(
            createJwtAuthenticationConverter()
        )));
        http
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
            // Check resource permissions with Keycloak policy enforcer
            .addFilterAfter(new ServletPolicyEnforcerFilter(httpRequest -> policyEnforcerConfig),
                BearerTokenAuthenticationFilter.class);
        return http.build();
    }

    private Converter<Jwt, JwtAuthenticationToken> createJwtAuthenticationConverter() {
        return jwt -> {
            final Collection<GrantedAuthority> authorities = new JwtGrantedAuthoritiesConverter().convert(jwt);
            final String userName = jwt.getClaimAsString("preferred_username");
            return new JwtAuthenticationToken(jwt, authorities, userName);
        };
    }

    @Bean
    public PolicyEnforcerConfig getPolicyEnforcerConfig() {
        try {
            final ObjectMapper mapper = new ObjectMapper(new SystemPropertiesJsonParserFactory());
            mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
            return mapper.readValue(
                getClass().getResourceAsStream("/policy-enforcer.json"),
                PolicyEnforcerConfig.class
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
