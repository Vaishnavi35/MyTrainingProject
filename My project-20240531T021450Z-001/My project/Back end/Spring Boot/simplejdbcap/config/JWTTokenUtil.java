package com.example.simplejdbcap.config;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
@Component
public class JWTTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 60;

	@Value("MySecrete")
	private String secret;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		 Date expiration = getExpirationDateFromToken(token);
		 System.out.println(expiration); 
			/*
			 * long addminutes =
			 * TimeUnit.MINUTES.toMillis(2); //Date minutes = new Date(addminutes);
			 * System.out.println(expiration.getTime() + addminutes);
			 * System.out.println(TimeUnit.MINUTES.toMillis(2));
			 * System.out.println(expiration.getTime()); long totalMinutes =
			 * expiration.getTime() + addminutes ; Date totalExpiration = new
			 * Date(totalMinutes); System.out.println(totalExpiration);
			 * System.out.println(totalExpiration.before(new Date()));
			 */
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		/*
		 * Date expiration = getExpirationDateFromToken(token);
		 * System.out.println(expiration); long addminutes =
		 * TimeUnit.MINUTES.toMillis(2); //Date minutes = new Date(addminutes);
		 * System.out.println(expiration.getTime() + addminutes);
		 * System.out.println(TimeUnit.MINUTES.toMillis(2));
		 * System.out.println(expiration.getTime()); long totalMinutes =
		 * expiration.getTime() + addminutes ; Date totalExpiration = new
		 * Date(totalMinutes); System.out.println(totalExpiration);
		 * System.out.println(totalExpiration.before(new Date())); return
		 * totalExpiration.before(new Date());
		 */
			return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()));
	}
	public String refreshToken(String token) {
		   Date createdDate = new Date();
		  System.out.println(createdDate);
		  long addminutes = TimeUnit.MINUTES.toMillis(2);
		  long totalMinutes = createdDate.getTime() + addminutes ;
		  final Date expirationDate = new Date(totalMinutes);
		  System.out.println(expirationDate);
		  final Claims claims = getAllClaimsFromToken(token);
		  claims.setIssuedAt(createdDate);
		  claims.setExpiration(expirationDate);
		  return Jwts.builder()
		    .setClaims(claims)
		    .signWith(SignatureAlgorithm.HS512, secret)
		    .compact();
		}

}

