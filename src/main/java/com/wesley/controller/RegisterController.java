package com.wesley.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wesley.entity.User;
import com.wesley.service.IUserService;
import com.wesley.util.LocaleUtil;
import com.wesley.util.VerificationCode;

@Controller("registerController")
@Scope("prototype")
public class RegisterController {

	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	@Autowired
	@Qualifier("verificationCode")
	private VerificationCode verificationCode;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model, HttpSession session) {
		
		// whether user has been log in {user, avatar}
		boolean isLogin = false;
		if (session.getAttribute("username") != null) {
			isLogin = true;
			model.addAttribute("loginUser", userService.findUserByName((String) session.getAttribute("username")));
		}
		model.addAttribute("isLogin", isLogin);
		
		//locale
		LocaleUtil.checkLocale(session, model);

		// get 3 users in random
		List<User> randomUserList = userService.getRandomUsers(userService.findAllUser(), 3);
		model.addAttribute("randomUserList", randomUserList);

		return "register";
	}
	
	@RequestMapping("/verifyCode")
	public void getVerificationCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		verificationCode.generateVerifyCode(request, response, session);
	}
	
	@RequestMapping(value="/checkUsername")  
    public @ResponseBody Map<String,Object> checkUsername(HttpServletRequest request,HttpServletResponse response) throws IOException{  
        String username = request.getParameter("username");

        Map<String,Object> map = new HashMap<String,Object>();  
        if(!userService.isUsernameExist(username)){  
            map.put("name_check", "success");  
        }else{  
            map.put("name_check", "failed");  
        }  
        return map;
	}
	
	@RequestMapping(value="/checkEmail")  
    public @ResponseBody Map<String,Object> checkEmail(HttpServletRequest request,HttpServletResponse response) throws IOException{  
        String email = request.getParameter("email");

        Map<String,Object> map = new HashMap<String,Object>();  
        if(!userService.isEmailExist(email)){  
            map.put("email_check", "success");  
        }else{  
            map.put("email_check", "failed");  
        }  
        return map;
	}
	
	@RequestMapping(value="/checkCode")  
    public @ResponseBody Map<String,Object> checkCode(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException{  
        String input_code = request.getParameter("code");
        System.out.println("input code :"+ input_code);
        String verifyCode = (String) session.getAttribute(VerificationCode.RANDOMCODEKEY);
        System.out.println("server code :"+ verifyCode);

        Map<String,Object> map = new HashMap<String,Object>();  
        if(input_code.toUpperCase().equals(verifyCode)){  
            map.put("code_check", "success");  
        }else{  
            map.put("code_check", "failed");  
        }  
        return map;
	}
	
	@RequestMapping(value="/doRegister")  
    public @ResponseBody Map<String,Object> doRegister(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException{  
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String pwd = request.getParameter("pwd");
        System.out.println("name:" + name + ", email:"+email+", gender:"+gender+", pwd:" + pwd);
        
        Map<String,Object> map = new HashMap<String,Object>();
        //save user to database
        User user = new User();
        user.setUserName(name);
        user.setPwd(pwd);
        user.setEmail(email);
        user.setGender(Integer.parseInt(gender));
        user.setJoinTime(new Timestamp(System.currentTimeMillis()));
        user.setGrade(0);
        user.setAvatar("Default.jpg");
        try {
        	userService.insert(user);
			map.put("register", "success");
		} catch (Exception e) {
			map.put("register", "failed");  
		}
          
        return map;
	}

}
