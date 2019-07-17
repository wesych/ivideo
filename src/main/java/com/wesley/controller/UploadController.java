package com.wesley.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wesley.util.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wesley.entity.User;
import com.wesley.entity.Video;
import com.wesley.ffmpeg.Capture;
import com.wesley.service.IUserService;
import com.wesley.service.IVideoService;
import com.wesley.util.LocaleUtil;

@Controller("uploadController")
@Scope("prototype")
public class UploadController {

	private static final String LOCAL_FFMPEG_PATH = PropertyUtil.getProperty("ffmpeg");
	private static final String LOCAL_UPLOAD_PATH = PropertyUtil.getProperty("upload");

	@Autowired
	@Qualifier("videoService")
	private IVideoService videoService;

	@Autowired
	@Qualifier("userService")
	private IUserService userService;

	/**
	 * Visit upload page.
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload(HttpSession session, Model model) {
		// login user
		boolean isLogin = false;
		if (session.getAttribute("username") != null) {
			isLogin = true;
			model.addAttribute("loginUser", userService.findUserByName((String) session.getAttribute("username")));
		}
		model.addAttribute("isLogin", isLogin);
		
		//locale
		LocaleUtil.checkLocale(session, model);

		// get top4 videos
		List<Video> top4List = videoService.getTop4VideosByPlayCount();
		model.addAttribute("top4List", top4List);

		return "upload";
	}

	/**
	 * Upload video.
	 * 
	 * @param file
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/doUpload", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> doUpload(@RequestParam("video") MultipartFile file, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		// user
		User loginUser = userService.findUserByName((String) session.getAttribute("username"));

		String title = request.getParameter("title");
		String label = request.getParameter("category");
		String desc = request.getParameter("description");
		String newFileName = (videoService.findAllVideo().size()+1) + ".mp4";
		
		// save video
		Map<String, Object> map = new HashMap<>();
		File targetFile = new File(LOCAL_UPLOAD_PATH, newFileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			map.put("upl", "failed");
		}
		
		// video capture
		String capture = request.getSession().getServletContext().getRealPath("capture"); 
		String newCapture = capture+"/" + (videoService.findAllVideo().size()+1) + ".jpg";
		
		//set path of ffmpeg.exe
		Capture.capture(targetFile.getAbsolutePath(), newCapture, LOCAL_FFMPEG_PATH);
		
		// video
		Video video = new Video();
		video.setVideoId(videoService.findAllVideo().size()+1);
		video.setTitle(title);
		video.setLabel(label);
		video.setDescription(desc);
		video.setUploadTime(new Timestamp(System.currentTimeMillis()));
		video.setFilename(newFileName);
		video.setUserId(loginUser.getUserId());
		video.setPlayCount(0);
		video.setCommentCount(0);
		video.setIsDelete(0);
		
		try {
			videoService.insertVideo(video);
			map.put("upl", "success");
		} catch (Exception e) {
			map.put("upl", "failed");
		}

		return map;
	}

}
