package com.projector.Dao;

import java.util.List;

import com.projector.model.Student;


public interface Dao {
	public String studentdetails(String student_name, String gender,String dob,String std,String score1,String score2,String practical1,String practical2);
	public List<Student> getStudentsDetails();
	public List<Student> checkDetails(String username, String password);
	public String updateStudentDetails(String name, String gender,String dob,String std,String score1,String score2,String practical1,String practical2,String id );
}
