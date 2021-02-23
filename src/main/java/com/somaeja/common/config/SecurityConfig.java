package com.somaeja.common.config;

import com.somaeja.common.config.jwt.JwtFilter;
import com.somaeja.common.config.jwt.JwtTokenProvider;
import com.somaeja.user.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider tokenProvider;

	private final UserAccountService userAccountService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors().disable()
			.httpBasic().disable()
			.formLogin().disable()

			.headers()
				.frameOptions()
				.sameOrigin()

			.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
			.authorizeRequests()
				// Oauth
				.antMatchers(HttpMethod.GET,"/posts").permitAll()
				.antMatchers(HttpMethod.GET,"/locations/{locationId}/posts").permitAll()
				.antMatchers(HttpMethod.GET,"/locations").permitAll()
				.antMatchers(HttpMethod.GET,"/users").permitAll()
				.antMatchers(HttpMethod.GET,"/users/restore").permitAll()
				.antMatchers(HttpMethod.POST,"/users/register").permitAll()
				.antMatchers(HttpMethod.POST,"/users/sign-in").permitAll()
			.anyRequest().authenticated()

			// JWT Handling
			.and()
			.exceptionHandling()
				.authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.accessDeniedHandler((request, response, accessDeniedException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN))

			// JWT Filter
			.and()
			.addFilterBefore(new JwtFilter(tokenProvider, userAccountService), UsernamePasswordAuthenticationFilter.class);
	}
}
