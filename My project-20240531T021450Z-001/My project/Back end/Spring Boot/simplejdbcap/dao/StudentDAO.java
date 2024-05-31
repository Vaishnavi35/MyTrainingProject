package com.example.simplejdbcap.dao;

import java.util.List;

import com.example.simplejdbcap.model.Student;

public interface StudentDAO {
	public String studentdetails(String student_name, String gender,String dob,String std,String score1,String score2,String practical1,String practical2);

	public   List<Student> getAllStudentsDetails(String tz);

	public List<Student> getStudentsDetails();
	
}
