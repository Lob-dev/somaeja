package com.somaeja.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

import static java.time.Instant.*;
import static java.util.Date.*;


@Slf4j
@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String secret;
	private Key key;

	@PostConstruct
	public void init() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String createToken(Long userId) {

		Date now = new Date();
		Date validity = from(ofEpochSecond(now.getTime() + Duration.ofDays(1).toSeconds()));

		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.setIssuedAt(now)
			.signWith(key, SignatureAlgorithm.HS512)
			.setExpiration(validity)
			.compact();
	}

	public String getUserId(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	public boolean validateToken(String jwt) {
		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(jwt);

			log.info("JWT validation success : subject = {}", claimsJws.getBody().getSubject());
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException exception) {
			log.info("Bad JWT signature. : message = {}", exception.getMessage());
		} catch (ExpiredJwtException exception) {
			log.info("The expired JWT token. : message = {}", exception.getMessage());
		} catch (UnsupportedJwtException exception) {
			log.info("JWT token not supported. : message = {}", exception.getMessage());
		} catch (IllegalArgumentException exception) {
			log.info("JWT token is invalid. : message = {}", exception.getMessage());
		}
		return false;
	}

}
