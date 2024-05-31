package com.example.simplejdbcap.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplejdbcap.config.JWTTokenUtil;
import com.example.simplejdbcap.config.MySessionListener;
import com.example.simplejdbcap.dao.StudentDAO;
import com.example.simplejdbcap.model.JWTResponse;
import com.example.simplejdbcap.model.Student;
import com.google.gson.Gson;

import antlr.StringUtils;
@CrossOrigin
@RestController

@RequestMapping("/StudentsAPI")

public class StudentController 
{
	@Autowired
	StudentDAO Studentdao;
	
	@RequestMapping(path = "/insertstudentdetails", method =RequestMethod.POST)
	public ResponseEntity<String> insertstudentdetails(@RequestBody Student student)
	{	
		
		
		/*
		 * Cookie cookie = new Cookie("platform","mobile"); cookie.setMaxAge(60*60*2);
		 * cookie.isHttpOnly(); response.addCookie(cookie);
		 */
		 
		/*
		 * String token = student.getToken(); System.out.println("token"+token); String
		 * username = jwtTokenUtil.getUsernameFromToken(token); if ( username == null )
		 * { System.out.println("name null"); throw new
		 * AuthenticationCredentialsNotFoundException( "token" ); } String
		 * refreshedToken = ""; if(jwtTokenUtil.canTokenBeRefreshed(token)) {
		 * 
		 * refreshedToken = jwtTokenUtil.refreshToken(token);
		 * System.out.println("refreshedToken"+refreshedToken); }
		 */

		/*
		 * HttpSession session = request.getSession(false); System.out.println("form");
		 * if(session == null){
		 * //LOG.info("Unable to find session. Creating a new session"); session =
		 * request.getSession(true); System.out.println("session:"+session); }
		 */
		System.out.println("form");
		HttpStatus status = HttpStatus.OK;
		Gson g=new Gson();
		String json_response = "";
		String studetvalue =Studentdao.studentdetails(student.name,student.gender,student.dob,student.std,student.score1,student.score2,student.practical1,student.practical2);
		json_response = "{\"status\":" + g.toJson(studetvalue)+"}";
		return new ResponseEntity<String>(json_response, status);
	}
	
	@RequestMapping(path="/getAllStudentDetails", method =RequestMethod.POST)
		public   List<Student> getAllStudentsDetails(@RequestBody Student student)
	{
		
		List<Student> getallstudentsdetails=Studentdao.getAllStudentsDetails(student.tz);
			return getallstudentsdetails;
			
	}
	@RequestMapping(path="/getStudentDetails", method =RequestMethod.POST)
	public   List<Student> getStudentsDetails()
	{
		 //Cookie[] cookies = request.getCookies();
		System.out.println("table");
		List<Student> getstudentsdetails=Studentdao.getStudentsDetails();
		return getstudentsdetails;
		
	}
	
}

