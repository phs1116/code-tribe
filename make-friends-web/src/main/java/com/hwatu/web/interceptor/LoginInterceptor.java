package com.hwatu.web.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by hwatu on 2017. 2. 24..
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			LoginCheck authCheck = hm.getMethodAnnotation(LoginCheck.class);
			Object userId = request.getSession().getAttribute(LoginAttributes.ID.name());
			if (userId == null && authCheck != null) {
				response.sendRedirect("/login?returnUrl="+ URLEncoder.encode(request.getRequestURL().toString(),"UTF-8"));
				return false;
			}else
				return true;
		}
		return super.preHandle(request,response,handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		String userId = (String) request.getSession().getAttribute(LoginAttributes.ID.name());
		if(userId != null && !isAjax(request)){
			modelAndView.addObject("currentUserId", userId);
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	public boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
}
