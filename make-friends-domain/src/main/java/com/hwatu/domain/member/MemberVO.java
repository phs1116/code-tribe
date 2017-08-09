package com.hwatu.domain.member;

import lombok.Data;

/**
 * Created by hwatu on 2017. 3. 7..
 */
@Data
public class MemberVO {
	private String userId;

	private String email;

	private String nickname;

	private String phoneNumber;

	public MemberVO(Member member) {
		userId = member.getUserId();
		email = member.getEmail();
		nickname = member.getNickname();
		phoneNumber = member.getPhoneNumber();
	}
}
