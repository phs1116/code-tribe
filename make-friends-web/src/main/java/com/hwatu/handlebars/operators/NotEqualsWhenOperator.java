package com.hwatu.handlebars.operators;

import lombok.AllArgsConstructor;

/**
 * Created by hwatu on 2017. 8. 8..
 */
@AllArgsConstructor
public class NotEqualsWhenOperator implements WhenOperator {
	private CompareOperatorUtils compareOperatorUtils;

	@Override
	public boolean operate(Object left, Object right) {
		if(left == null || right == null) {
			return left != right;
		}

		return compareOperatorUtils.compare(left, right) != 0;
	}
}
