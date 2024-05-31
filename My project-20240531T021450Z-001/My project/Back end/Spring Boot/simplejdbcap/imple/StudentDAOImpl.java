package com.example.simplejdbcap.imple;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.simplejdbcap.dao.StudentDAO;
import com.example.simplejdbcap.model.Student;
@Repository
public class StudentDAOImpl implements StudentDAO 
{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	

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
				message= "Student detail inserted successfully";
			}
		}
		catch (Exception e) 
		{
			message = e.toString();
		}
		return message;
	}

	public   List<Student> getAllStudentsDetails(String tz)
	{
		String sql="SELECT name,gender,DATE_FORMAT(CONVERT_TZ(dob,'+00:00',?),\"%d %b, %Y(%a)\")as dob ,  std as std ,score1,score2,practical1,practical2 FROM student ORDER BY std ASC";
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
		try 
		{
			List<Student> article = jdbcTemplate.query(sql, rowMapper,tz);
			System.out.println( article.toString());
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
}



