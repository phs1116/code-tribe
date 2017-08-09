package com.hwatu.domain.member;

import com.hwatu.domain.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hwatu on 2017. 2. 21..
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public MemberDto findById(String userId) {
		Member member = memberRepository.findByUserId(userId);
		return modelMapper.map(member, MemberDto.class);
	}

	@Override
	public MemberDto findByIdAndPassword(String userId, String password) throws NotIdException {
		if (userId == null || userId.equals(""))
			throw new NotIdException(ExceptionMessage.NOT_INPUT_ID);
		Member member = memberRepository.findByUserIdAndPassword(userId, password);
		Validate.notNull(member, "아이디가 없습니다.");
		return modelMapper.map(member, MemberDto.class);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(MemberDto memberDto) throws NotMatchedPasswordException {
		memberRepository.save(mappingMemberModel(memberDto));
	}

	public Member mappingMemberModel(MemberDto memberDto) throws NotMatchedPasswordException {
		Member member = memberRepository.findByUserId(memberDto.getUserId());
		if (member == null)
			member = modelMapper.map(memberDto, Member.class);
		else if (member.matchPassword(memberDto.getPassword()))
			member.updateMemberInfo(memberDto);
		else if (!member.matchPassword(memberDto.getPassword()))
			throw new NotMatchedPasswordException(ExceptionMessage.NOT_MATCHED_PWD);
		return member;
	}
}
