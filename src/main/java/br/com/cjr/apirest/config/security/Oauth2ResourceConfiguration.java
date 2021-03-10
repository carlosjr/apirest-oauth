package br.com.cjr.apirest.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class Oauth2ResourceConfiguration extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "resource_id";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID)
				 .stateless(true);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors()
			.and().authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
			.antMatchers("/", "/log/**", "/oauth/**").permitAll()
			.anyRequest().authenticated()
			.and().logout()
			.and().formLogin()
			.and().httpBasic()
			.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
		
	}
	
}
