package com.wesley.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieTool {
	
	public static final String COOKIE_PATH = "/ivideo";

	public static final String COOKIE_NAME = "ivideo_cookie_username";
	public static final String COOKIE_VALUE = "ivideo_cookie_password";

	public static final int COOKIE_EXPIRY = 60 * 60 * 24;
	
	/**
	 * Add cookie.
	 * @param name
	 * @param value
	 * @param response
	 */
	public static void addCookie(String name, String value, HttpServletResponse response) {
		Cookie userNameCookie = new Cookie(COOKIE_NAME, name);  
	    Cookie passwordCookie = new Cookie(COOKIE_VALUE, value);  
	    userNameCookie.setMaxAge(COOKIE_EXPIRY);  
	    userNameCookie.setPath(COOKIE_PATH);  
	    passwordCookie.setMaxAge(COOKIE_EXPIRY);  
	    passwordCookie.setPath(COOKIE_PATH);  
	    response.addCookie(userNameCookie);  
	    response.addCookie(passwordCookie);  
	}
	
	/**
	 * Delete cookie.
	 * @param name
	 * @param value
	 * @param response
	 */
	public static void deleteCookie(String name, String value, HttpServletResponse response){
	    Cookie userNameCookie = new Cookie(COOKIE_NAME, name);  
	    Cookie passwordCookie = new Cookie(COOKIE_VALUE, value);  
	    userNameCookie.setMaxAge(0);  
	    userNameCookie.setPath(COOKIE_PATH);  
	    passwordCookie.setMaxAge(0);  
	    passwordCookie.setPath(COOKIE_PATH);  
	    response.addCookie(userNameCookie);  
	    response.addCookie(passwordCookie);  
	}

}
