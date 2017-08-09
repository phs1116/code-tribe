package com.hwatu.domain;

import lombok.Data;

/**
 * Created by hwatu on 2017. 3. 8..
 */
@Data
public class ErrorMessageDto {
	private String resultMessage;
	private String returnUrl;

	public ErrorMessageDto() {
	}
}
