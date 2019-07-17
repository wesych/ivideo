package com.wesley.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wesley.service.IUserService;
import com.wesley.util.CookieTool;
import com.wesley.util.PublicKeyMap;
import com.wesley.util.RSAUtils;

@Controller("loginController")
@Scope("prototype")
public class LoginController {

	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(LoginController.class);

	@Autowired
	@Qualifier("userService")
	private IUserService userService;

	@RequestMapping("loginpage")
	public String page() {
		return "login";
	}

	@RequestMapping("rsa")
	public @ResponseBody Map<String, Object> rsa() throws IOException {
		PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("map", publicKeyMap);
		return map;
	}

	@RequestMapping("login")
	public @ResponseBody Map<String, Object> login(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) throws Exception {
		// fetch username and transfer_encrypted password
		String login_name = request.getParameter("username");
		String login_pwd = request.getParameter("password");
		// log.warn("encript password:" + login_pwd);

		// decrypt
		String dpwd = RSAUtils.decryptStringByJs(login_pwd);

		boolean isLogin = false;
		Map<String, Object> map = new HashMap<String, Object>();
		if (userService.isUserExist(login_name, dpwd)) {
			session.setAttribute("username", login_name);
			isLogin = true;
			model.addAttribute("loginUser", userService.findUserByName(login_name));
			map.put("login", "success");

			// set cookie (20170817)
			CookieTool.addCookie(login_name, dpwd, response);

		} else {
			map.put("login", "failed");
		}
		model.addAttribute("isLogin", isLogin);
		return map;
	}

	@RequestMapping("logout")
	public @ResponseBody Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) {
		// delete cookie
		String login_name = request.getParameter("username");
		String login_pwd = request.getParameter("password");
		CookieTool.deleteCookie(login_name, login_pwd, response);

		// invalidate session
		session.removeAttribute("username");
		model.addAttribute("isLogin", false);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("logout", "success");
		return map;
	}

}
