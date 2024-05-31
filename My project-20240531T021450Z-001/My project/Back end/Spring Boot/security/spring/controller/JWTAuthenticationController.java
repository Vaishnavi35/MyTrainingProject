package com.security.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.security.spring.config.JWTTokenUtil;
import com.security.spring.model.JWTRequest;
import com.security.spring.model.JWTResponse;
import com.security.spring.model.User;
import com.security.spring.model.UserDto;
import com.security.spring.repository.UserRepository;
import com.security.spring.service.CustomerUserDetailService;


@RestController
@CrossOrigin
public class JWTAuthenticationController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTTokenUtil jwtTokenUtil;
	
	@Autowired
	private CustomerUserDetailService userDetailsService;
   
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JWTRequest authenticationRequest,User user,UsernamePasswordAuthenticationToken authentication) throws Exception {
		HttpStatus status = null;
		String response="";
		Gson g=new Gson();
		String json_response = "";
		final String username = userDetailsService.findUsername(authenticationRequest.getUsername());
		final String password = userDetailsService.findpassword(authenticationRequest.getUsername());
		String presentedPassword = authenticationRequest.getPassword();
		Boolean checkName = authenticationRequest.getUsername().matches(username);
		if(!checkName) {
			json_response = "{\"status\":" + g.toJson("OK") + ",\"User_Name\":"+ g.toJson(authenticationRequest.getUsername()) 
			+",\"Password\":" + g.toJson(authenticationRequest.getPassword())+",\"Message\":" + g.toJson("username wrong ")+ "}";
			status = HttpStatus.UNAUTHORIZED;
			response= json_response;	
		}else {
		Boolean Password = passwordEncoder.matches(presentedPassword,password);
		//Boolean checkName = authenticationRequest.getUsername().matches(username);
		//System.out.println(checkName);
		if(Password){
				authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
				final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
				final String token = jwtTokenUtil.generateToken(userDetails);
				json_response = "{\"status\":" + g.toJson("OK") + ",\"User_Name\":"+ g.toJson(authenticationRequest.getUsername()) 
				+",\"Password\":" + g.toJson(authenticationRequest.getPassword())+",\"Token\":" + g.toJson(token)+",\"Message\":" + g.toJson("Successfully")+ "}";
				new JWTResponse(token);
				response = json_response;
				status = HttpStatus.OK;
		}else{
			json_response = "{\"status\":" + g.toJson("OK") + ",\"User_Name\":"+ g.toJson(authenticationRequest.getUsername()) 
			+",\"Password\":" + g.toJson(authenticationRequest.getPassword())+",\"Message\":" + g.toJson("password wrong")+ "}";
			status = HttpStatus.UNAUTHORIZED;
			response= json_response;
		}
		}
		return new ResponseEntity<>(response,status); 
		}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String getEmployees() {
			return "Welcome" ;
			
		}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	
}