package com.projector.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RequestMapping("/StudentsAPI")

public class Controller {
	@Autowired
	Dao Studentdao;
	@Autowired
	LoginController longincon;
	private HttpSession session=null;
	
	@RequestMapping(path = "/insertstudentdetails", method =RequestMethod.POST)
	public ResponseEntity<String> insertstudentdetails(@RequestBody Student student)
	{	
		String response;
		Gson g=new Gson();
		String json_response = "";
		HttpStatus status = null;
		 String loggedInUser=longincon.getsessiontime();
		 if(loggedInUser == "failure") {
			 try {
			 session.invalidate();
			 }
			 catch(NullPointerException e) {
				 System.out.println("Session invalidate: "+ e);
			 }
			 json_response = "{\"Message\": \"Your session has expired!\"}";
			 System.out.println("Status "+ loggedInUser);
			 status = HttpStatus.BAD_REQUEST;
		 }
		 else if(loggedInUser == "null"){
			 json_response = "{\"Message\": \"Please you login first\"}";
			 System.out.println("Status "+ loggedInUser);
			 status = HttpStatus.BAD_REQUEST;
		 }
		 else {
			 	status = HttpStatus.OK;
			 	response =Studentdao.studentdetails(student.name,student.gender,student.dob,student.std,student.score1,student.score2,student.practical1,student.practical2);
			 	json_response = "{\"Message\":" +g.toJson(response) +"}";
		 }
		return new ResponseEntity<String>(json_response, status);
	}
	
	
	@RequestMapping(path="/getStudentDetails", method =RequestMethod.POST)
	public   List<Student> getStudentsDetails(HttpServletResponse response) throws IOException
	{
		Gson g=new Gson();
		String json_response;
		String loggedInUser=longincon.getsessiontime();
		System.out.println("Status is  "+ loggedInUser);
		if(loggedInUser == "failure") {
			
			 json_response = "{\"Message\": \"Your session has expired!\"}"; ;
			PrintWriter pw = null; 
			  pw = response.getWriter();
			  response.setContentType("application/json"); 
			  pw.print(json_response);
			  response.setStatus(HttpServletResponse.SC_BAD_REQUEST, "Session Expired");
			  pw.close();
			  session.invalidate();
		 }
		else if(loggedInUser == "null") {
			json_response = "{\"Message\": \"Please you login first\"}"; ;
			PrintWriter pw = null; 
			  pw = response.getWriter();
			  response.setContentType("application/json"); 
			  pw.print(json_response);
			  response.setStatus(HttpServletResponse.SC_BAD_REQUEST, "Login first");
			  pw.close();
		}
		else {
		List<Student> getstudentsdetails=Studentdao.getStudentsDetails();
		return getstudentsdetails;
		 }
		return null;
		
	}
	
	@RequestMapping(path = "/updatestudentdetails", method =RequestMethod.POST)
	public ResponseEntity<String> update(@RequestBody Student student)
	{	
		String response;
		Gson g=new Gson();
		String json_response = "";
		HttpStatus status = null;
		 String loggedInUser=longincon.getsessiontime();
		 if(loggedInUser == "failure") {
			 try {
				 session.invalidate();
				 }
				 catch(NullPointerException e) {
					 System.out.println("Session invalidate: "+ e);
				 }
			 json_response = "{\"Message\": \"Your session has expired!\"}";
			 System.out.println("Status "+ loggedInUser);
			 status = HttpStatus.BAD_REQUEST;
		 }
		 else {
			 	System.out.println("form");
			 	status = HttpStatus.OK;
			 	response =Studentdao.updateStudentDetails(student.name,student.gender,student.dob,student.std,student.score1,student.score2,student.practical1,student.practical2,student.id);
			 	json_response = "{\"Message\":" +g.toJson(response) +"}";
		 }
		return new ResponseEntity<String>(json_response, status);
	}
}
