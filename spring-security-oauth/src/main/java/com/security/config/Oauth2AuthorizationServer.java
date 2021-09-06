package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

	@Value("${user.oauth.clientId}")
	private String clientId;
	@Value("${user.oauth.clientSecret}")
	private String clientSecret;
	@Value("${user.oauth.redirectUris}")
	private String redirectURI;
	
	@Autowired
	PasswordEncoder encoder;
	
	 @Override
	    public void configure(
	        AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
	        oauthServer.tokenKeyAccess("permitAll()")
	            .checkTokenAccess("isAuthenticated()");
	    }

	    @Override
	    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	        clients.inMemory()
	            .withClient(clientId)
	            .secret(encoder.encode(clientSecret))
	            .authorizedGrantTypes("authorization_code")
	            .scopes("user_info")
	            .autoApprove(true)
	            .redirectUris(redirectURI);
	    }
}
