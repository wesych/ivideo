package com.wesley.util;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

public class LocaleUtil {
	
	public static void checkLocale(HttpSession session, Model model) {
		boolean isUS = false;
		Object locale = session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		if (locale != null) {
			if (locale.toString().equals("en_US")) {
				isUS = true;
			} else {
				isUS = false;
			}
		}
		model.addAttribute("isUS", isUS);
	}

}
