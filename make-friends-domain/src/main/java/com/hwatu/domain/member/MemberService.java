package com.hwatu.domain.member;

/**
 * Created by hwatu on 2017. 2. 21..
 */
public interface MemberService {
	MemberDto findById(String userId);
	MemberDto findByIdAndPassword(String userId, String password) throws NotIdException;

	void save(MemberDto member) throws NotMatchedPasswordException;
}
