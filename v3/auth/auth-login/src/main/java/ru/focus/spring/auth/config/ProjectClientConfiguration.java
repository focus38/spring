package ru.focus.spring.auth.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

public class ProjectClientConfiguration {

    private final OAuth2ClientProperties oAuth2ClientProperties;

    public ProjectClientConfiguration(final OAuth2ClientProperties oAuth2ClientProperties) {
        this.oAuth2ClientProperties = oAuth2ClientProperties;
    }

    @Bean
    @ConditionalOnBean({OAuth2AuthorizedClientService.class, ClientRegistrationRepository.class})
    @ConditionalOnMissingBean
    public OAuth2AuthorizedClientManager feignOAuth2AuthorizedClientManager(final ClientRegistrationRepository repository,
                                                                            final OAuth2AuthorizedClientService service) {
        return new AuthorizedClientServiceOAuth2AuthorizedClientManager(repository, service);
    }

    @Bean
    @ConditionalOnBean(OAuth2AuthorizedClientManager.class)
    public OAuth2AccessTokenInterceptor defaultOAuth2AccessTokenInterceptor(final OAuth2AuthorizedClientManager manager) {
        return new OAuth2AccessTokenInterceptor(
            oAuth2ClientProperties.getRegistration().keySet().iterator().next(),
            manager
        );
    }
}
