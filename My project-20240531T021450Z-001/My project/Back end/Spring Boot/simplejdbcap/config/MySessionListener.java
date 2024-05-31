package com.example.simplejdbcap.config;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MySessionListener implements HttpSessionListener {
	//private HttpSession session=null;
	
	  @Override
	  public void sessionCreated(HttpSessionEvent se) {
	      System.out.println("-- HttpSessionListener#sessionCreated invoked --");
	      HttpSession session = se.getSession();
	      System.out.println("session"+session);
	      System.out.println("session id: " + session.getId());
	     // String un="value";
	     // session.setAttribute("username", un);
	     // System.out.println("session username: " + session.getAttribute("username"));
	      session.setMaxInactiveInterval(60 * 3);//in seconds
	  }

	  @Override
	  public void sessionDestroyed(HttpSessionEvent se) {
		  System.out.println("-- HttpSessionListener#sessionDestroyed invoked --");
			/*
			 * HttpSession httpSession = se.getSession(); long lastAccessedTime =
			 * httpSession.getLastAccessedTime(); int maxInactiveTime =
			 * httpSession.getMaxInactiveInterval() * 1000; //millis long currentTime =
			 * System.currentTimeMillis(); if((currentTime - lastAccessedTime) >=
			 * maxInactiveTime ){
			 * 
			 * }
			 */
	      
	  }
		/*
		 * public String getUserName() { return (String)
		 * session.getAttribute("username"); }
		 */
	  
	}