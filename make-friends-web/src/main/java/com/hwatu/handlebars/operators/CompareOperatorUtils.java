package com.hwatu.handlebars.operators;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by hwatu on 2017. 8. 8..
 */
public class CompareOperatorUtils {

	public int compare(Object left, Object right) {
		//둘 다 숫자면 BigDecimal 형으로 변환 후 비교
		if (left instanceof Number && right instanceof Number) {
			return compareNumber((Number) left, (Number) right);
		}
		return compare((Comparable) left, (Comparable) right);
	}

	public int compare(Comparable left, Comparable right) {
		return left.compareTo(right);
	}

	public int compareNumber(Number left, Number right) {
		return convertToBigdecimal(left).compareTo(convertToBigdecimal(right));
	}

	private BigDecimal convertToBigdecimal(Number number) {
		if (number instanceof BigDecimal) {
			return (BigDecimal) number;
		} else if (number instanceof BigInteger) {
			return new BigDecimal((BigInteger) number);
		}
		return BigDecimal.valueOf(number.longValue());
	}
}
