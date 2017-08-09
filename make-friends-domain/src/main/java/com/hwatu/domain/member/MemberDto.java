package com.hwatu.domain.member;

import lombok.Data;

/**
 * Created by hwatu on 2017. 2. 22..
 */

@Data
public class MemberDto {
	private String userId;

	private String email;

	private String password;

	private String newPassword;

	private String nickname;

	private String phoneNumber;
}
