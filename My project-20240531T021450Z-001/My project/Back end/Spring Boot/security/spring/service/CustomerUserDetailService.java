package com.security.spring.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.spring.model.User;
import com.security.spring.model.UserDto;
import com.security.spring.repository.UserRepository;


@Service
public class CustomerUserDetailService  implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		else 
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
public User save(UserDto user) { 
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword())); return
		userRepository.save(newUser); 
		}
public  String findUsername(String username) {
try {
			User user = userRepository.findByUsername(username);
			return user.getUsername();
		}
		catch (NullPointerException e) {
			return e.toString();
		}
		
	}
	public String findpassword(String username) {
		// TODO Auto-generated method stub
		try {
		User user = userRepository.findByUsername(username);
		return user.getPassword();
		}
		catch (NullPointerException e) {
			return e.toString();
		}
		
	}
}
