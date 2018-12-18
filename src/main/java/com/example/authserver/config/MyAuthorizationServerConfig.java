package com.example.authserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.inject.Inject;

@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final AuthenticationManager authenticationManager;
    private final MyAppUserDetailsService userDetailsService;

    @Inject
    public MyAuthorizationServerConfig(AuthenticationManager authenticationManager,
                                       MyAppUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //@formatter:off
        clients.inMemory()
                .withClient("live-test")
                .secret("{noop}bGl2ZS10ZXN0")
                .authorizedGrantTypes("password")
                .scopes("all")
                //.autoApprove("um-webapp")
                .accessTokenValiditySeconds(3600);
        ;
        //@formatter:on
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //@formatter:off
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .tokenEnhancer(tokenConverter())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        //@formatter:on
    }

    private TokenStore tokenStore() {
        return new JwtTokenStore(tokenConverter());
    }

    private JwtAccessTokenConverter tokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey("abcdefgh");
        return tokenConverter;
    }
}
