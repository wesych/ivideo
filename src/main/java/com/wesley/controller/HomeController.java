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

@Controller("homeController")
@Scope("prototype")
public class HomeController {

	@Autowired
	@Qualifier("videoService")
	private IVideoService videoService;

	@Autowired
	@Qualifier("userService")
	private IUserService userService;

	private boolean isLogin = false;

	@RequestMapping("/")
	public String showHomePage(HttpSession session, Model model) {
		// user info
		if (session.getAttribute("username") != null) {
			isLogin = true;
			model.addAttribute("loginUser", userService.findUserByName((String) session.getAttribute("username")));
		} 

		model.addAttribute("isLogin", isLogin);
		view(model);
		
		//locale
		LocaleUtil.checkLocale(session, model);
		
		return "index";
	}

	private void view(Model model) {
		// Video list
		List<Video> videoList = videoService.findAllVideo();

		// top4 video list
		List<Video> top4List = videoService.getTop4VideosByPlayCount();
		model.addAttribute("top4List", top4List);

		// Music video
		List<Video> allMusicList = videoService.getSpecificLabelVideo(videoList, "music");
		List<Video> random2MusicList = videoService.getTopNumVideos(allMusicList, 3);
		model.addAttribute("random2MusicList", random2MusicList);

		// Amuse video
		List<Video> allAmuseList = videoService.getSpecificLabelVideo(videoList, "amuse");
		List<Video> random2AmuseList = videoService.getTopNumVideos(allAmuseList, 2);
		model.addAttribute("random2AmuseList", random2AmuseList);

		// Funny video
		List<Video> allFunnyList = videoService.getSpecificLabelVideo(videoList, "funny");
		List<Video> random2FunnyList = videoService.getTopNumVideos(allFunnyList, 2);
		model.addAttribute("random2FunnyList", random2FunnyList);

		// Sort videos by comment count.
		List<Video> sortByCommentList = videoService.sortByCommentCount(videoList);
		Video recommendedVideo = sortByCommentList.get(0);
		String recommendedPreview = "capture/" + recommendedVideo.getVideoId() + ".jpg";
		model.addAttribute("recommendedVideo", recommendedVideo);
		model.addAttribute("recommendedPreview", recommendedPreview);
	}

}
