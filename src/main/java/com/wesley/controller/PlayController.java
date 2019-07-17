package com.wesley.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wesley.entity.Comment;
import com.wesley.entity.User;
import com.wesley.entity.Video;
import com.wesley.service.ICommentService;
import com.wesley.service.IUserService;
import com.wesley.service.IVideoService;
import com.wesley.util.LocaleUtil;
import com.wesley.util.PropertyUtil;

@Controller("playController")
@Scope("prototype")
public class PlayController {
	
	private static final String LOCALHTTPSERVER = PropertyUtil.getProperty("address");
	
	@Autowired
	@Qualifier("videoService")
	private IVideoService videoService;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	@Autowired
	@Qualifier("commentService")
	private ICommentService commentService;

	/**
	 * Show video play page.
	 * @param video_id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/play/{video_id}")
	public String playVideo(@PathVariable("video_id") int video_id, Model model, HttpSession session) {

		// whether user has been log in {user, avatar}
		boolean isLogin = false;
		if (session.getAttribute("username") != null) {
			isLogin = true;
			User loginUser = userService.findUserByName((String) session.getAttribute("username"));
			model.addAttribute("loginUser", loginUser);
			
			// whether user has collected this video
			String id = String.valueOf(video_id);
			boolean isCollected = userService.isCollected(loginUser, id);
			model.addAttribute("isCollected", isCollected);
		}
		model.addAttribute("isLogin", isLogin);
		
		//locale
		LocaleUtil.checkLocale(session, model);
		
		// video object
		Video chosenVideo = videoService.findVideoById(video_id);
		model.addAttribute("chosenVideo", chosenVideo);
		
		// video address
		String videoSource= LOCALHTTPSERVER + "videos/" + video_id + ".mp4";
		model.addAttribute("videoSource", videoSource);
		
		// video uploader
		User uploader = userService.findUserById(chosenVideo.getUserId());
		model.addAttribute("uploader", uploader);
		
		// video comments
		List<Comment> commentList = commentService.findCommentsByVideoId(video_id);
		model.addAttribute("commentList", commentList);
		model.addAttribute("currentTime", new Timestamp(System.currentTimeMillis()));
		
		
		// top4 video list
		List<Video> top4List = videoService.getTop4VideosByPlayCount();
		model.addAttribute("top4List", top4List);
		
		return "player";
	}
	
	/**
	 * Collect this video.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/collect")  
    public @ResponseBody Map<String,Object> collect(Model model, HttpServletRequest request,HttpServletResponse response) throws IOException{  
		// user 
		String username = request.getParameter("username");
		System.out.println("collect username :" + username);
		User user = userService.findUserByName(username);
		
		// video
		String id = request.getParameter("video_id");
		System.out.println("collect video_id:" + id);
		
		// collect this video
		Map<String,Object> map = new HashMap<String,Object>();  
		try {
			userService.addVideoCollection(user, id);
			model.addAttribute("isCollected", true);
			map.put("col", "success");   
		} catch (DataAccessException e) {
			model.addAttribute("isCollected", false);
			map.put("col", "failed");   
		}
		
        return map;
	}
	
	/**
	 * Detach this video from collection.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/detach")  
    public @ResponseBody Map<String,Object> detach(Model model, HttpServletRequest request,HttpServletResponse response) throws IOException{  
		// user 
		String username = request.getParameter("username");
		User user = userService.findUserByName(username);
		
		// video
		String id = request.getParameter("video_id");
		
		// remove collection of this video
		Map<String,Object> map = new HashMap<String,Object>();  
		try {
			userService.removeVideoFromCollection(user, id);
			map.put("det", "success");   
		} catch (DataAccessException e) {
			map.put("det", "failed");   
		}
		map.put("det", "success");
		
        return map;
	}
	
	/**
	 * Add comment for this video.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/comment")  
    public @ResponseBody Map<String,Object> comment(Model model, HttpServletRequest request,HttpServletResponse response) throws IOException{  
		// user 
		String user_id = request.getParameter("user_id");
		User user = userService.findUserById(Integer.parseInt(user_id));
		
		// video
		String video_id = request.getParameter("video_id");
		
		// comment content
		String content = request.getParameter("content");
		
		// add comment
		Comment comment = new Comment();
		comment.setUserId(user.getUserId());
		comment.setVideoId(Integer.parseInt(video_id));
		comment.setContent(content);
		comment.setCommentTime(new Timestamp(System.currentTimeMillis()));
		comment.setIsDelete(0);
		
		// comment_count + 1
		Video video = videoService.findVideoById(Integer.parseInt(video_id));
		video.setCommentCount(video.getCommentCount() + 1);
		videoService.updateVideo(video);		
		
		Map<String,Object> map = new HashMap<String,Object>(); 
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		String time = sdf.format(comment.getCommentTime());
		try {
			commentService.insertComment(comment);
			map.put("comment", "success");
			map.put("time", time);
		} catch (DataAccessException e) {
			map.put("comment", "failed");   
		}
        
        return map;
	}
	
	/**
	 * Begin to play this video.
	 * @param model
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/addplaycount")  
    public @ResponseBody Map<String,Object> addplaycount(Model model, HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException{  
		String video_id = request.getParameter("video_id");
		
		// if login, add play record
		if (session.getAttribute("username") != null) {
			User loginUser = userService.findUserByName((String) session.getAttribute("username"));
			userService.addPlayRecord(loginUser, video_id);
		}
		
		// play count + 1
		Map<String,Object> map = new HashMap<String,Object>(); 
		Video video = videoService.findVideoById(Integer.parseInt(video_id));
		video.setPlayCount(video.getPlayCount() + 1);
		try {
			videoService.updateVideo(video);
			map.put("plc", "success");
		} catch (Exception e) {
			map.put("plc", "failed");
		}
		
		return map;
	}
	
}
