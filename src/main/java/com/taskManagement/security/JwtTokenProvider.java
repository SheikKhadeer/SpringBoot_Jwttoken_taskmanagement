package com.taskManagement.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.taskManagement.exception.ApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private static final Key secretKey = generateKey();

	public static Key generateKey() {
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
		System.out.println("generated key :  " + encodedKey);
		return key;
	}

	public String generateToken(Authentication authentication) {
		String email = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + 1000 * 60 * 60);// one hour expiry
System.out.println(secretKey);
		String token = Jwts.builder().setSubject(email).setIssuedAt(currentDate).setExpiration(expireDate)
				.signWith(secretKey).compact();
		return token;
	}

	public String getEmailFromToken(String token) {
		Claims cliams = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		return cliams.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		}catch(Exception e) {
			throw new ApiException("token issue :  "+e.getMessage());
		}
		
		
	}

}
