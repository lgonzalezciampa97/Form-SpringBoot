package com.bolsadeideas.springboot.form.app.interceptors;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("TimeElapsedInterceptor")
public class TimeElapsedInterceptor implements HandlerInterceptor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeElapsedInterceptor.class);
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("post")) {
			return true;
		}
		
		if(handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			LOGGER.info("controller´s method: " + method.getMethod().getName());
		}
		
		LOGGER.info("TimeElapsedInterceptor: preHandle() login...");
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		Random random = new Random();
		Integer delay = random.nextInt(100);
		Thread.sleep(delay);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("post")) {
			return;
		}
		
		long endTime = System.currentTimeMillis();
		long startTime = (Long) request.getAttribute("startTime");
		long loggedTime = endTime - startTime;
		if(handler instanceof HandlerMethod && modelAndView != null) {
			modelAndView.addObject("loggedTime",loggedTime);
		}
		LOGGER.info("Time Elapsed: " + loggedTime + " ms");
		LOGGER.info("TimeElapsedInterceptor: postHandle() exit...");
		
	}

}
