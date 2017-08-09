package com.hwatu.domain.member;

/**
 * Created by hwatu on 2017. 2. 25..
 */
public class NotMatchedPasswordException extends Exception {
	public NotMatchedPasswordException(String message) {
		super(message);
	}
}
