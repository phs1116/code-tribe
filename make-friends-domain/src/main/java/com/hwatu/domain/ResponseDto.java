package com.hwatu.domain;

import lombok.Data;

/**
 * Created by hwatu on 2017. 3. 8..
 */
@Data
public class ResponseDto<T> {
	boolean status;
	String resultMessage;
	T results;

	public ResponseDto(boolean status, String message, T results) {
		this.status = status;
		this.resultMessage = message;
		this.results = results;
	}
}
