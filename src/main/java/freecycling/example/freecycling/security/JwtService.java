package freecycling.example.freecycling.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * We use the jjwt library to create JWTs.
 * 
 * More information about this library is available at
 * https://github.com/jwtk/jjwt
 */

@Service
public class JwtService {

	private final SecretKey key;

	public JwtService(@Value("${app.security.jwt-secret}") String jwtSecret) {
		this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

	public boolean isValid(String token) {
		try {
			return Jwts.parser()
					.verifyWith(key)
					.build()
					.parseSignedClaims(token)
					.getPayload()
					.getExpiration()
					.after(new Date(System.currentTimeMillis()));
		} catch (Exception ex) {

		}
		return false;
	}

	public String getSubject(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	public String makeJwt(String userid) {
		return Jwts.builder()
				.subject(userid)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(key)
				.compact();
	}

}
