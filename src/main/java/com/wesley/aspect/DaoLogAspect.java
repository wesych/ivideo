package com.wesley.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component("daoLogAspect")
public class DaoLogAspect {

	private static final Log log = LogFactory.getLog(DaoLogAspect.class);

	public void doAfter(JoinPoint jp) {
		String logStr = "{Finish running method(" + jp.getSignature().getName() + ")"
				+ " in class:" + jp.getTarget().getClass().getName();
		log.info(logStr);
	}
}
