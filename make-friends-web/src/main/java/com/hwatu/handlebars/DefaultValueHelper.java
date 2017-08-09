package com.hwatu.handlebars;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

/**
 * Created by hwatu on 2017. 8. 8..
 */
public class DefaultValueHelper implements Helper<Object> {
	public static final String HELPER_NAME = "default";
	@Override
	public CharSequence apply(Object context, Options options) throws IOException {
		if(context != null){
			return String.valueOf(context);
		}
		return options.param(0, "");
	}
}
