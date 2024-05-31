package com.projector.Imp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.projector.Dao.Dao;
import com.projector.model.Student;
@Repository
public class DaoImplemetation implements Dao{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public String studentdetails(String name, String gender,String dob,String std,String score1,String score2,String practical1,String practical2) 
	{
		String message="";
		String sql_update = "insert into student (name,gender,dob,std,score1,score2,practical1,practical2) values (?,?,?,?,?,?,?,?)";
		try 
		{
			int row = jdbcTemplate.update(sql_update, name,gender,dob,std,score1,score2,practical1,practical2);
			System.out.println(row);
			if (row > 0) 
			{
				message= "Student details inserted successfully!";
			}
		}
		catch (Exception e) 
		{
			message = e.toString();
		}
		return message;
	}

	

	public List<Student> getStudentsDetails()
	{
		String sql="SELECT * FROM student";
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
		try 
		{
			List<Student> article = jdbcTemplate.query(sql, rowMapper);
			if (article.size() > 0) 
			{
				return article;
			}
		}
		catch(EmptyResultDataAccessException e)
		{
		System.out.println( e.toString());
		}
		catch(Exception e) 
		{
		System.out.println( e.toString());
		}
		return null;
	}
	public List<Student> checkDetails( String username, String password)
	{
		String currectPassword = password;
		System.out.println("cp:"+currectPassword);
		String sql = "SELECT * FROM login WHERE username = ?";
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
		try 
		{
			List<Student> article = jdbcTemplate.query(sql, rowMapper,username);
			System.out.println("selected Row:"+article);
			System.out.println("selected Row Size:"+article.size());
			if (article.size() > 0) 
			{
				return article;
			}
		}
		catch(EmptyResultDataAccessException e )
		{
			System.out.println( e.toString());
		}
		return null;
	}
	
	public String updateStudentDetails(String name, String gender,String dob,String std,String score1,String score2,String practical1,String practical2,String id ) 
	{
		String message="";
		String sql_update = "UPDATE student SET name=?,gender = ?,dob = ?,std = ?,score1 = ?, score2 = ?,practical1 = ?,practical2 = ? WHERE id=?";
		try 
		{
			int row = jdbcTemplate.update(sql_update,name,gender,dob,std,score1,score2,practical1,practical2,id);
			System.out.println(row);
			if (row > 0) 
			{
				message= "Student details updated successfully!";
			}
		}
		catch (Exception e) 
		{
			message = e.toString();
		}
		return message;
	}
}
