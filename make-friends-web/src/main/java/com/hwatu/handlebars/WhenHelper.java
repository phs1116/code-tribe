package com.hwatu.handlebars;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.google.common.collect.Maps;
import com.hwatu.handlebars.operators.CompareOperatorUtils;
import com.hwatu.handlebars.operators.EqualsWhenOperator;
import com.hwatu.handlebars.operators.NotEqualsWhenOperator;
import com.hwatu.handlebars.operators.WhenOperator;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.util.Map;

/**
 * Created by hwatu on 2017. 8. 8..
 */
public class WhenHelper implements Helper {
	public static final String HELPER_NAME = "when";
	private Map<String, WhenOperator> operatorMap = Maps.newHashMap();

	private WhenHelper() {
	}

	@Override
	public Object apply(Object context, Options options) throws IOException {
		Object left = context;
		Object operator = options.param(0, null);
		Object right = options.param(1, null);

		Validate.notNull(operator, "operator is null");
		WhenOperator whenOperator = operatorMap.get(operator);
		Validate.notNull(whenOperator, operator+"'s operator not exists");

		if (whenOperator.operate(left, right)){
			return options.fn();
		}
		return options.inverse();
	}

	public static WhenHelper getWhenHelper(){
		WhenHelper whenHelper = new WhenHelper();
		CompareOperatorUtils compareOperatorUtils = new CompareOperatorUtils();
		whenHelper.operatorMap.put("equals", new EqualsWhenOperator(compareOperatorUtils));
		whenHelper.operatorMap.put("notEquals", new NotEqualsWhenOperator(compareOperatorUtils));
		return whenHelper;
	}
}

