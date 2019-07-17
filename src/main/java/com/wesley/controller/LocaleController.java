package com.wesley.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Controller
@Scope("prototype")
public class LocaleController {

	@RequestMapping("/switch")
	public @ResponseBody Map<String, Object> switchLang(HttpSession session) {
		
		// change locale in session
		Object locale = session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		if (locale == null) {
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.US);
		} else {
			if (locale.toString().equals("en_US")) {
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.CHINA);
			}
			if (locale.toString().equals("zh_CN")) {
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.US);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("swl", "success");
		return map;
	}
}
