package com.hwatu.web.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by hwatu on 2017. 2. 24..
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;
			AuthCheck authCheck = hm.getMethodAnnotation(AuthCheck.class);

				Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
				String pathUserId = (String) pathVariables.get("userId");
				String userId = (String)request.getSession().getAttribute(LoginAttributes.ID.name());
				if(!(pathUserId!= null && pathUserId.equals(userId)) && authCheck != null) {
					response.sendRedirect("/main");
					return false;
				}
				else {
				return true;
			}
		}
		return super.preHandle(request, response, handler);
	}
}
