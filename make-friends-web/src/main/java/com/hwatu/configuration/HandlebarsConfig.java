package com.hwatu.configuration;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import com.hwatu.handlebars.DefaultValueHelper;
import com.hwatu.handlebars.FormatDateHelper;
import com.hwatu.handlebars.WhenHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hwatu on 2017. 8. 8..
 */

@Configuration
public class HandlebarsConfig {
	public static final String HANDLEBARS_VIEW_PREFIX = "classpath:templates/";
	public static final String HANDLEBARS_VIEW_SUFFIX = ".hbs";

	@Bean
	public HandlebarsViewResolver handelbarsViewResolver(){
		HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
		//viewResolver.setOrder(1);
		viewResolver.setFailOnMissingFile(false);
		viewResolver.setPrefix(HANDLEBARS_VIEW_PREFIX);
		viewResolver.setSuffix(HANDLEBARS_VIEW_SUFFIX);

		//개발환경에선 끄고, 프로덕션 환경에서는 키자.
		viewResolver.setCache(false);


		return viewResolver;
	}

	@Bean
	public Handlebars handlebars(HandlebarsViewResolver handlebarsViewResolver){
		Handlebars handlebars = handlebarsViewResolver.getHandlebars();
		handlebars.setPrettyPrint(true);
		handlebars.registerHelper(WhenHelper.HELPER_NAME, WhenHelper.getWhenHelper());
		handlebars.registerHelper(FormatDateHelper.HELPER_NAME, new FormatDateHelper());
		handlebars.registerHelper(DefaultValueHelper.HELPER_NAME, new DefaultValueHelper());
		return handlebars;
	}
}
