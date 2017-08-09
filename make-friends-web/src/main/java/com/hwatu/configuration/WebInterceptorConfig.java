package com.hwatu.configuration;

import com.hwatu.web.interceptor.AuthInterceptor;
import com.hwatu.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by hwatu on 2017. 8. 8..
 */
@Configuration
public class WebInterceptorConfig extends WebMvcConfigurerAdapter{
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor());
		registry.addInterceptor(new AuthInterceptor());
		super.addInterceptors(registry);
	}
}
