package ru.focus.spring.auth.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.util.StreamUtils;
import ru.focus.spring.auth.exception.AppException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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

    @Bean
    public ErrorDecoder errorDecoder() {
        return (s, response) -> {
            final String message = getBody(response);
            return new AppException(response.status(), response.request().url(), message);
        };
    }

    private String getBody(final Response response) {
        try (final InputStream stream = response.body().asInputStream()) {
            return StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Не удалось считать сообщение об ошибке";
        }
    }
}
