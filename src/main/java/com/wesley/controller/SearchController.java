package com.wesley.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wesley.entity.Video;
import com.wesley.service.IUserService;
import com.wesley.service.IVideoService;
import com.wesley.util.LocaleUtil;

@Controller("searchController")
@Scope("prototype")
public class SearchController {
	
	@Autowired
	@Qualifier("videoService")
	private IVideoService videoService;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	@RequestMapping(value="/search")
	public String search(HttpSession session, Model model, String key) {
		// user login session
		boolean isLogin = false;
		if (session.getAttribute("username") != null) {
			isLogin = true;
			model.addAttribute("loginUser", userService.findUserByName((String) session.getAttribute("username")));
		} 
		model.addAttribute("isLogin", isLogin);
		
		//locale
		LocaleUtil.checkLocale(session, model);
		
		// top4 
		List<Video> top4List = videoService.getTop4VideosByPlayCount();
		model.addAttribute("top4List", top4List);
		
		// search result
		if (key == null || key.length() == 0) {
			return null;
		} else {
			List<Video> searchList = videoService.getSearchList(key);
			model.addAttribute("searchList", searchList);
			return "search";
		}
	}

}
