package com.wesley.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wesley.entity.User;
import com.wesley.entity.Video;
import com.wesley.service.IUserService;
import com.wesley.service.IVideoService;
import com.wesley.util.Encript;
import com.wesley.util.LocaleUtil;

@Controller("spaceController")
@Scope("prototype")
public class SpaceController {
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	@Autowired
	@Qualifier("videoService")
	private IVideoService videoService;
	
	/**
	 * Personal index page.
	 * @param user_id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/myspace/{user_id}", method=RequestMethod.GET)
	public ModelAndView visitPersonalSpace(@PathVariable("user_id")int user_id, Model model, HttpSession session) {
		// user
		User user = userService.findUserById(user_id);
		model.addAttribute("loginUser", user);
		model.addAttribute("isLogin", true);
		
		//locale
		LocaleUtil.checkLocale(session, model);
		
		// play record
		List<Video> recordList = videoService.getPlayRecordList(user.getPlayRecord());
		model.addAttribute("recordList", recordList);		
		
		// collection videos
		List<Video> collectionList = videoService.getCollectionList(user.getVideoCollection());
		model.addAttribute("collectionList", collectionList);
		
		// upload videos
		List<Video> uploadList= new ArrayList<Video>();
		for(Video vv : videoService.findAllVideo()){
		   if(vv.getUserId() == user_id){
			   uploadList.add(vv);
		   }
		}
		model.addAttribute("uploadList", uploadList);
		
		return new ModelAndView("space");
	}
	
	/**
	 * Modify personal info.
	 * @param file
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/doModify", method = RequestMethod.POST)  
    public @ResponseBody Map<String,Object> doModify(@RequestParam("newAvatar") MultipartFile file, HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException{  
        // user
		User loginUser = userService.findUserByName((String) session.getAttribute("username"));
		
		// password
		String oldpwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        
        Map<String,Object> map = new HashMap<String,Object>();
        if (Encript.SHA256(oldpwd).endsWith(loginUser.getPwd())) {
			//update pwd
        	loginUser.setPwd(Encript.SHA256(newPwd));
        	
        	//update avatar
        	String path = request.getSession().getServletContext().getRealPath("avatar");
            String imagename = loginUser.getUserId()+".jpg";
            File targetFile = new File(path, imagename);
            
            if (file.getSize() != 0) {
            	if (!targetFile.exists()) {
                    targetFile.mkdirs();
                } else {
                	targetFile.delete();
                }
            	try {
                    file.transferTo(targetFile);
                } catch (Exception e) {
                	map.put("mod", "failed");
                }
                loginUser.setAvatar(imagename);
			}
            
            try {
				userService.update(loginUser);
				map.put("mod", "success");
			} catch (Exception e) {
				map.put("mod", "failed");
			}
		} else {
			map.put("mod", "failed");
			map.put("pwd", "failed");
		}
        
        return map;
	}

}
