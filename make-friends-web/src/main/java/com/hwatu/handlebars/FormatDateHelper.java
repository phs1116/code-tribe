package com.hwatu.handlebars;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hwatu on 2017. 8. 8..
 */
public class FormatDateHelper implements Helper<Date>{
	public static final String HELPER_NAME = "formatDate";
	public static final String DEFUALT_FOMRAT = "yyyy/MM/dd HH:mm";
	@Override
	public Object apply(Date context, Options options) throws IOException {
		Validate.notNull(context,"context is null");
		Validate.notNull(options, "options is null");

		String format = options.param(0, DEFUALT_FOMRAT);

		return formatDate(context, format);
	}
	private Object formatDate(Date context, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(context);
	}
}
