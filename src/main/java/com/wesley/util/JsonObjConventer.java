package com.wesley.util;

import com.alibaba.fastjson.JSONObject;
import com.wesley.entity.User;

public class JsonObjConventer {

	public static JSONObject transferUser(User user){
		JSONObject obj = new JSONObject();
		obj.put("user_id",user.getUserId());
		obj.put("user_name",user.getUserName());
		obj.put("user_avatar",user.getAvatar());
		
		return obj;
	}
}
