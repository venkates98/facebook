package com.facebook.utitility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "#Hari#123@";
	private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);
	private static final int TOKEN_EXPIRATION_HOURS = 10;

	public String generateToken(String username) {
		return JWT.create().withSubject(username).withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_HOURS * 3600 * 1000)) // 10 hours
				.sign(ALGORITHM);
	}

	public String extractUsername(String token) {
		DecodedJWT decodedJWT = decodeToken(token);
		return decodedJWT.getSubject();
	}

	public boolean validateToken(String token, String username) {
		String tokenUsername = extractUsername(token);
		return (tokenUsername.equals(username) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		DecodedJWT decodedJWT = decodeToken(token);
		Date expiration = decodedJWT.getExpiresAt();
		return expiration.before(new Date());
	}

	private DecodedJWT decodeToken(String token) {
		JWTVerifier verifier = JWT.require(ALGORITHM).build();
		return verifier.verify(token);
	}
}
