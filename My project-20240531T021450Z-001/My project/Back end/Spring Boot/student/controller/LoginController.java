package com.student.controller;

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
import com.student.Dao.Dao;
import com.student.model.Student;

@CrossOrigin(origins = "http://localhost")
@RestController
public class LoginController {
	private HttpSession session=null;
	@Autowired
	Dao Studentdao;
	String Id = null;
	String SecondId = null;
	
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
						if(Id == null) {
							Id = session.getId();
						}else {
							SecondId = session.getId();
						}
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

@RequestMapping(path = "/checkLoginStatus", method =RequestMethod.POST)
public ResponseEntity<String> checkLoginStatus(HttpServletRequest request)
{	
	String response;
	Gson g=new Gson();
	String json_response = "";
	HttpStatus status = null ;
	String currentId = null;
	Boolean firstIdMatch = null;
	Boolean secondIdMatch = null;
	session=request.getSession();
	System.out.println("session:"+session);
	currentId = session.getId();
	System.out.println("Current id:"+session.getId());
	System.out.println("1st id:"+Id);
	System.out.println("2nd id:"+SecondId);
	try{
	if(Id.matches(currentId))
	{
		System.out.println("firstIdMatch:"+Id.matches(currentId));
		json_response = "{\"Message\": \"User already logged in\"}";
		status = HttpStatus.OK;
		
	}else if(SecondId.matches(currentId)) {
		System.out.println("secondIdMatch:"+SecondId.matches(currentId));
		json_response = "{\"Message\": \"User already logged in\"}";
		status = HttpStatus.OK;
	}
	}catch(NullPointerException e)
	{
		System.out.println(e.toString());
		json_response = "{\"Message\": \"Session is null\"}";
		status = HttpStatus.BAD_REQUEST;
	}catch(IllegalArgumentException e) {
		System.out.println(e.toString());
		json_response = "{\"Message\": \"Session is null\"}";
		status = HttpStatus.BAD_REQUEST;
	}
	
	return new ResponseEntity<String>(json_response, status);
}

public String checkId(HttpServletRequest request)
{
	String response = null;
	String currentId = null;
	try{
		session=request.getSession();
		System.out.println("session:"+session);
		currentId = session.getId();
		System.out.println("Current id:"+session.getId());
		System.out.println("1st id:"+Id);
		System.out.println("2nd id:"+SecondId);
	if(Id.matches(currentId))
	{
		response = "success";
		System.out.println("firstIdMatch:"+Id.matches(currentId));
		
	}else if(SecondId.matches(currentId)) {
		response = "success";
		System.out.println("secondIdMatch:"+SecondId.matches(currentId));
	}
	
	}catch(NullPointerException e)
	{
		System.out.println(e.toString());
		//response = null;
	}
	if(response == null) {
		try {
		if(SecondId.matches(currentId)) {
			response = "success";
			System.out.println("secondIdMatch:"+SecondId.matches(currentId));
		}
		
		}catch(NullPointerException e)
		{
			System.out.println(e.toString());
			//response = null;
		}
	
	}
	
	return response;
}

@RequestMapping(path="/logout", method =RequestMethod.POST)
public ResponseEntity<String> logout(HttpServletRequest request) {
	String json_response;
	set_Id_as_null(request);
	System.out.println("1st id:"+Id);
	System.out.println("2nd id:"+SecondId);
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
public void set_Id_as_null(HttpServletRequest request){
	String currentId = null;
	try{
		System.out.println("Hi:");
		session=request.getSession();
		System.out.println("session:"+session);
		System.out.println("Current id:"+session.getId());
		currentId = session.getId();
	}catch(Exception e)
	{
	System.out.println(e.toString());
	}
	try {
		if(currentId.matches(Id)) {
			Id = null;
		}else if(currentId.matches(SecondId)) {
			SecondId = null;
		}
		}catch(NullPointerException e)
		{
			System.out.println(e.toString());
			SecondId = null;
		}
	 return;
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
		System.out.println(e.toString());
	}
return response;
}
}
