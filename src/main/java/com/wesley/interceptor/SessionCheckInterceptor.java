package com.wesley.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SessionCheckInterceptor implements HandlerInterceptor {

	private static final Log log = LogFactory.getLog(SessionCheckInterceptor.class);
	
	/**
	 * if return 'false', intercept it.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request , HttpServletResponse response, Object arg2) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		
		if (username != null) {
			// login successfully
			return true;
		} else {
			// logout or not login
			log.info("intercept url:" + request.getRequestURI());
			response.sendRedirect(basePath);  
			return false;
		}
	}

	/**
	 * Run this method if "preHandle" return 'true'.
	 * run after 'controller' but before return 'modelandview'.
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * Run this method if "preHandle" return 'true'.
	 * used in handling exception or log, and clear resources.
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}
}
