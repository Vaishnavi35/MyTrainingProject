package com.projector.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.projector.Dao.Dao;
import com.projector.model.Student;

@CrossOrigin(origins = "http://localhost")
@RestController
public class LoginController {
	private HttpSession session=null;
	@Autowired
	Dao Studentdao;
	
@RequestMapping(path="/authenticate", method =RequestMethod.POST)
 public ResponseEntity<String> authenticate(@RequestBody Student student,HttpServletRequest request)
{
	HttpStatus status = null;
	Gson g=new Gson();
	String json_response = "";
	String password=student.password;
	List<Student> checkdetails=Studentdao.checkDetails(student.username,student.password);
	
	if(checkdetails !=null) {
		String EncriptPassword = null;
		Boolean isPasswordMatch = null;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		for(Student s :checkdetails ) {
			 EncriptPassword = s.getPassword();
			 System.out.println("Encoded Password is : " + EncriptPassword);
			 isPasswordMatch = passwordEncoder.matches(password, EncriptPassword);
			 System.out.println("isPasswordMatch : " + isPasswordMatch);
			 if(isPasswordMatch) {
				 json_response =  "{\"Message\": \"Login Successfully!\"}";
					status = HttpStatus.OK;
					try{
						session=request.getSession();
						System.out.println("session:"+session);
						
						System.out.println("session id:"+session.getId());
						long currentTime = System.currentTimeMillis();
						System.out.println("Login Time:"+new Date(currentTime));
						session.setAttribute("last_activity", currentTime);
					}catch(Exception e)
					{
					System.out.println(e.toString());
					}
				 break;
			 }
		}
		System.out.println("Password is         : " + password);
		System.out.println("Encoded Password is : " + EncriptPassword);
		
		if (!isPasswordMatch)  {
				status = HttpStatus.UNAUTHORIZED;
				json_response = "{\"Message\": \"Wrong Password\"}";
				}
		}else {
				status = HttpStatus.UNAUTHORIZED;
				json_response =  "{\"Message\": \"Wrong Username\"}";
		
	}
	
	return new ResponseEntity<String>(json_response, status);
	
}

@RequestMapping(path="/logout", method =RequestMethod.POST)
public ResponseEntity<String> logout() {
	String json_response;
	try {
	session.invalidate();
	}
	catch(NullPointerException e)
	{
		System.out.println(e.toString());
	}
	catch(IllegalStateException e) {
		System.out.println(e.toString());
	}
	System.out.println("Loggedout Successfully!");
	HttpStatus status = HttpStatus.OK;
	json_response =  "{\"Message\": \"Loggedout Successfully!\"}";
	return new ResponseEntity<String>(json_response, status);
	
}

public String getsessiontime()
{
	String response="";
	long currentTime;
	currentTime = System.currentTimeMillis();
	System.out.println("Current Time:"+new Date(currentTime));
	try {
	long last_activity = (Long) session.getAttribute("last_activity");
	System.out.println("Last Activity Time:"+new Date(last_activity));
	if( (currentTime - last_activity) > (120*1000)) {
		response= "failure";
		
	}else {
		currentTime = System.currentTimeMillis();
	    session.setAttribute("last_activity:", currentTime);
		response= "success";
	}}
	catch(IllegalStateException e)
	{
		response = "failure";
	}
	catch(NullPointerException e)
	{
		response = "null";
	}
return response;
}
}
