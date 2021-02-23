package com.somaeja.common.config.jwt;

import com.somaeja.user.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// JWT 를 처리하기 위한 Custom Filter,
// GenericFilterBean 인터페이스는 Spring 이 인식하는 간단한 javax.servlet.Filter 의 Bean 등록 방식이다.

// 	  지원하는 메서드 : addFilterBefore, addFilterAfter, addFilterAt, addFilter
//    ex) 적용하는 법
//    http
//			.addFilterAfter( new JwtFilter(), BasicAuthenticationFilter.class);
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
	public static final String AUTHORIZATION_HEADER = "Authorization";
	private final JwtTokenProvider tokenProvider;
	private final UserAccountService userAccountService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String jwt = resolveToken(httpRequest);
		String requestURI = httpRequest.getRequestURI();

		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			UserDetails userDetails = userAccountService.loadUserByUsername(tokenProvider.getUserId(jwt));
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, jwt, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.debug("authentication saved '{}' in the security context, uri: {}", authentication.getName(), requestURI);
		} else {
			log.debug("Not valid JWT token, uri: {}", requestURI);
		}

		chain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
