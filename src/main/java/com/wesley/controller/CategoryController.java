package com.wesley.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wesley.entity.Video;
import com.wesley.service.IUserService;
import com.wesley.service.IVideoService;
import com.wesley.util.LocaleUtil;

@Controller("categoryController")
@Scope("prototype")
public class CategoryController {
	
	@Autowired
	@Qualifier("videoService")
	private IVideoService videoService;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;

	@RequestMapping("/category/{label}")
	public String showCategoryVideos(HttpSession session, Model model, @PathVariable("label")String label) {
		//label
		model.addAttribute("label", label);
		
		// user login session
		boolean isLogin = false;
		if (session.getAttribute("username") != null) {
			isLogin = true;
			model.addAttribute("loginUser", userService.findUserByName((String) session.getAttribute("username")));
		} 
		model.addAttribute("isLogin", isLogin);
		
		//locale
		LocaleUtil.checkLocale(session, model);
		
		// category videos
		List<Video> videos = videoService.findAllVideo();
		List<Video> categoryList = videoService.getSpecificLabelVideo(videos, label);
		model.addAttribute("categoryList", categoryList);
				
		return "category";
	}
}
