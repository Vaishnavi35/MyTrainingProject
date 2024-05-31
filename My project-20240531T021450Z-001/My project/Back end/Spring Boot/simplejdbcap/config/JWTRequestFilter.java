package com.example.simplejdbcap.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.simplejdbcap.imple.CustomerUserDetailService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	private CustomerUserDetailService jwtUserDetailsService;

	@Autowired
	private JWTTokenUtil jwtTokenUtil;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		//HttpSessionEvent se = null;
		
		  final String requestTokenHeader = request.getHeader("Authorization"); //final
		 // Integer SESSION_TIMEOUT_IN_SECONDS = 60 * 3; 
		  String username = null; 
		  String jwtToken = null;
		  
		  // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
		   if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) { 
			   jwtToken = requestTokenHeader.substring(7); 
			   try { 
			  username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			 // HttpSession httpSession = request.getSession(false); 
				//long lastAccessedTime = httpSession.getLastAccessedTime(); 
				//System.out.println("lastAccessedTime:"+lastAccessedTime);
				//int maxInactiveTime = httpSession.getMaxInactiveInterval() * 1000; //millis 
			//	System.out.println("maxInactiveTime:"+maxInactiveTime);
			//	long currentTime = System.currentTimeMillis();
				//System.out.println("currentTime:"+currentTime);
			//	if((currentTime - lastAccessedTime) >= maxInactiveTime ){
			//		System.out.println("session expired!");
			//	}else
			//	{
				//	httpSession = request.getSession(true);
				//}
		  
				/*
				 * HttpSession session = request.getSession();
				 * System.out.println("session"+session); if (session == null) {
				 * System.out.println("-- creating new session in the servlet --"); session =
				 * request.getSession(true); HttpSessionEvent se = null;
				 * 
				 * sessionListener.sessionCreated(se);
				 * System.out.println("-- session created in the servlet --"); //todo populate
				 * some user objects in session
				 * 
				 * 
				 * }
				 */
		  
		  //request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT_IN_SECONDS);
		  System.out.println("username"+username); 
		  } 
			   catch (IllegalArgumentException e)
		  { 
				   System.out.println("Unable to get JWT Token"); 
		  } catch (ExpiredJwtException e) { 
			  System.out.println("JWT Token has expired"); 
			  } 
			} else {
		  System.out.println("JWT Token has not created with Bearer"); 
		  }
		  
		  System.out.println("gghg");
		  
		  if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		  System.out.println("gghg"); 
		  UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
		  System.out.println("gghg"); 
		  if (jwtTokenUtil.validateToken(jwtToken,userDetails)) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken( userDetails, null,
		  userDetails.getAuthorities()); 
			usernamePasswordAuthenticationToken .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		  SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); 
		  } 
		  }
		 
		chain.doFilter(request, response);
	}
}
