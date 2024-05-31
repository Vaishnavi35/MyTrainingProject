
  package com.security.spring.config;
  
  import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable; import
  javax.servlet.http.HttpServletRequest; import
  javax.servlet.http.HttpServletResponse; import
  org.springframework.security.core.AuthenticationException; import
  org.springframework.security.web.AuthenticationEntryPoint; import
  org.springframework.stereotype.Component;
  
  @Component 
  public class JWTAuthenticationEntryPonit implements
  AuthenticationEntryPoint, Serializable { 
	  private static final long
  serialVersionUID = -7858869558953243875L;
  
  @Override public void commence(HttpServletRequest request,
  HttpServletResponse response, AuthenticationException authException) throws
  IOException { System.out.println("hi");
  	 
  	  PrintWriter pw = null; pw = response.getWriter();
	  response.setContentType("text/html"); 
	  pw.println("Your session has expired!");
	  response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	  pw.close();
   }
  
  }
 
