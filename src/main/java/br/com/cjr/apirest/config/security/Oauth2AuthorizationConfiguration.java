package br.com.cjr.apirest.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter{

		private static final String AUTHORIZATION_CODE = "authorization_code";
		private static final String REFRESH_TOKEN = "refresh_token";
		private static final String GRANT_TYPE = "password";
		private static final String OPENID = "openid";
		
		private static final String WEB_CLIENT_ID = "luizalabs_foodsolution_web";
		private static final String APP_CLIENT_ID = "luizalabs_foodsolution_app";
		
		@Value("${jwt.expiration.webAccess}")
		private String expWebAccess;
		
		@Value("${jwt.expiration.webRefresh}")
		private String expWebRefresh;
		
		@Value("${jwt.expiration.appAccess}")
		private String expAppAccess;
		
		@Value("${jwt.expiration.appRefresh}")
		private String expAppRefresh;
		
		@Value("${jwt.webSecret}")
		private String webSecret;
		
		@Value("${jwt.appSecret}")
		private String appSecret;
		
		@Autowired
		private AuthenticationManager authenticationManager;
		
		@Autowired
		private PasswordEncoder passwordEncoder;
		
		@Autowired
		private UserDetailsService userService;

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()
				.withClient(WEB_CLIENT_ID)
				.secret(passwordEncoder.encode(webSecret))
				.accessTokenValiditySeconds(Integer.parseInt(expWebAccess))
				.refreshTokenValiditySeconds(Integer.parseInt(expWebRefresh))
				.authorizedGrantTypes(AUTHORIZATION_CODE, REFRESH_TOKEN, GRANT_TYPE)
				.scopes(OPENID)
			.and()
				.withClient(APP_CLIENT_ID)
				.secret(passwordEncoder.encode(appSecret))
				.accessTokenValiditySeconds(Integer.parseInt(expAppAccess))
				.refreshTokenValiditySeconds(Integer.parseInt(expAppRefresh))
				.authorizedGrantTypes(AUTHORIZATION_CODE, REFRESH_TOKEN, GRANT_TYPE)
				.scopes(OPENID);
		}
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
			TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
			tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
			
			endpoints
				.tokenStore(tokenStore())
				.tokenEnhancer(tokenEnhancerChain)
				.reuseRefreshTokens(false)	
				.userDetailsService(userService)
				.authenticationManager(authenticationManager);
		}
		
		@Bean
		public JwtAccessTokenConverter accessTokenConverter() {
			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
			converter.setSigningKey(webSecret);

			return converter;
		}
		
		@Bean
		public TokenStore tokenStore() {
			return new JwtTokenStore(accessTokenConverter());
		}
		
		@Bean
		public TokenEnhancer tokenEnhancer() {
		    return new CustomTokenEnhancer();
		}
		
}
