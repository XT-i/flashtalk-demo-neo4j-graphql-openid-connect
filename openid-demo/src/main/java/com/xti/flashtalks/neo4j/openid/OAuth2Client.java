package com.xti.flashtalks.neo4j.openid;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import javax.annotation.Resource;

import static java.util.Arrays.asList;
import static org.springframework.security.oauth2.common.AuthenticationScheme.form;
import static org.springframework.security.oauth2.common.AuthenticationScheme.header;

@Configuration
@EnableOAuth2Client
public class OAuth2Client {
    private String clientId = "{clientId}";
    private String clientSecret = "{clientSecret}";

    @Bean
    public OAuth2ProtectedResourceDetails oAuthDetails() {
        AuthorizationCodeResourceDetails oAuth2Details = new AuthorizationCodeResourceDetails();
        oAuth2Details.setAuthenticationScheme(form);
        oAuth2Details.setClientAuthenticationScheme(header);
        oAuth2Details.setClientId(clientId);
        oAuth2Details.setClientSecret(clientSecret);
        oAuth2Details.setUserAuthorizationUri("http://127.0.0.1:8080/c2id-login-page-js");
        oAuth2Details.setAccessTokenUri("http://127.0.0.1:8080/c2id/token");
        oAuth2Details.setScope(asList("openid profile email"));
        return oAuth2Details;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection") // Provided by Spring Boot
    @Resource
    private OAuth2ClientContext oAuth2ClientContext;

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public OAuth2RestOperations googleOAuth2RestTemplate() {
        return new OAuth2RestTemplate(oAuthDetails(), oAuth2ClientContext);
    }
}
