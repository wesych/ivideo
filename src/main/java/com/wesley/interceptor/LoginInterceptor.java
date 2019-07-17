package com.wesley.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wesley.service.IUserService;
import com.wesley.util.CookieTool;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	@Qualifier("userService")
	private IUserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String username = (String) request.getSession().getAttribute("username");

		if (username == null) {
			String loginCookieUserName = "";
			String loginCookiePassword = "";

			Cookie[] cookies = request.getCookies();
			if (null != cookies) {
				for (Cookie cookie : cookies) {
					if (CookieTool.COOKIE_NAME.equals(cookie.getName())) {
						loginCookieUserName = cookie.getValue();
						System.out.println("cookie_name:" + loginCookieUserName);
					} else if (CookieTool.COOKIE_VALUE.equals(cookie.getName())) {
						loginCookiePassword = cookie.getValue();
						System.out.println("cookie_value:" + loginCookiePassword);
					}
				}
				if (!"".equals(loginCookieUserName) && !"".equals(loginCookiePassword)) {
					if (userService.isUserExist(loginCookieUserName, loginCookiePassword)) {
						request.getSession().setAttribute("username", loginCookieUserName);
					}
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
