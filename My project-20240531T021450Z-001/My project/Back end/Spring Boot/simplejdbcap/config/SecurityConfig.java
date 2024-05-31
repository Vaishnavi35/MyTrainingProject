package com.example.simplejdbcap.config;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration 
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	  
	  @Autowired
		private JWTAuthenticationEntryPonit jwtAuthenticationEntryPoint;
	  @Autowired 
	  private UserDetailsService userDetailsService;
	  @Autowired
		private JWTRequestFilter jwtRequestFilter;
	  
	  @Bean public AuthenticationProvider authProvider() {
		   DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		   provider.setUserDetailsService(userDetailsService);
		   provider.setPasswordEncoder(passwordEncoder()); 
		   return provider; 
		   }
		
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception {
				httpSecurity.csrf().disable()
				.authorizeRequests().antMatchers("/authenticate", "/register").permitAll().
				anyRequest().authenticated().and().
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().
				sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				httpSecurity.cors();
				httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
				
		}

}

