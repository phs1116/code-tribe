package com.hwatu.domain.memberGathering;

import com.hwatu.domain.ExceptionMessage;
import com.hwatu.domain.gathering.GatheringDetailDto;
import com.hwatu.domain.gathering.GatheringRepository;
import com.hwatu.domain.member.MemberDto;
import com.hwatu.domain.member.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hwatu on 2017. 2. 24..
 */
@Service
@Transactional(readOnly = true)
public class MemberGatheringServiceImpl implements MemberGatheringService {
	private static final int PAGE_SIZE = 2;
	private static final String SORT_TYPE = "joinAt";

	@Autowired
	private MemberGatheringRepository memberGatheringRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private GatheringRepository gatheringRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * @title 모임 참가
	 * @param memberGatheringDto
	 */
	@Transactional(readOnly = false)
	@Override
	public AttendInfoDto save(MemberGatheringDto memberGatheringDto) throws AlreadyAttendedException {
		MemberGathering checkMemberGathering = memberGatheringRepository.findByGathering_GatheringNoAndMember_UserId(
			memberGatheringDto.getGatheringNo(), memberGatheringDto.getUserId());
		MemberGathering memberGathering = createMemberGathering(memberGatheringDto);

		if (checkMemberGathering != null)
			throw new AlreadyAttendedException(ExceptionMessage.ALREADY_ATTENDED);
		if (memberGathering.getGathering().getUserId().equals(memberGatheringDto.getUserId()))
			throw new AlreadyAttendedException(ExceptionMessage.IS_HOST);

		memberGatheringRepository.save(memberGathering);
		return getAttendInfoDto(memberGathering);

	}

	/**
	 * 모임 신청 취소
	 * @param memberGatheringDto
	 * @return
	 * @throws NotAttendedException
	 */
	@Transactional(readOnly = false)
	@Override
	public AttendInfoDto delete(MemberGatheringDto memberGatheringDto) throws NotAttendedException {
		MemberGathering checkMemberGathering = memberGatheringRepository.findByGathering_GatheringNoAndMember_UserId(
			memberGatheringDto.getGatheringNo(), memberGatheringDto.getUserId());
		if (checkMemberGathering == null)
			throw new NotAttendedException(ExceptionMessage.NOT_ATTEND);

		memberGatheringRepository.delete(checkMemberGathering);
		return getAttendInfoDto(createMemberGathering(memberGatheringDto));
	}

	
	private AttendInfoDto getAttendInfoDto(MemberGathering memberGathering) {
		AttendInfoDto attendInfoDto = new AttendInfoDto();
		attendInfoDto.setGatheringNo(memberGathering.getGathering().getGatheringNo());
		attendInfoDto.setUserId(memberGathering.getMember().getUserId());
		attendInfoDto.setNickname(memberGathering.getMember().getNickname());
		return attendInfoDto;
	}

	/**
	 * @title 회원이 참가한 모임 리스트 조회
	 * @param userId
	 * @param page
	 * @return
	 */
	@Override
	public List<GatheringDetailDto> findByUserId(String userId, Integer page) {
		Pageable pageable = new PageRequest(page, PAGE_SIZE, new Sort(Sort.DEFAULT_DIRECTION, SORT_TYPE));
		Page<MemberGathering> memberGatheringPage = memberGatheringRepository.findByMember_UserId(userId, pageable);
		List<GatheringDetailDto> gatheringDetailDtoList = memberGatheringPage.getContent().stream().map(
			(mg) -> modelMapper.map(mg.getGathering(), GatheringDetailDto.class)).collect(
			Collectors.toList());
		return gatheringDetailDtoList;
	}

	/**
	 * @title 해당 모임에 참가한 회원 리스트 조회
	 * @param gatheringNo
	 * @param page
	 * @return
	 */
	@Override
	public List<MemberDto> findByGatheringNo(Long gatheringNo, Integer page) {
		Pageable pageable = new PageRequest(page, PAGE_SIZE, new Sort(Sort.DEFAULT_DIRECTION, SORT_TYPE));
		Page<MemberGathering> memberGatheringPage = memberGatheringRepository.findByGathering_GatheringNo(gatheringNo, pageable);
		List<MemberDto> memberDtoList = memberGatheringPage.getContent().stream().map(
			(mg) -> modelMapper.map(mg.getMember(), MemberDto.class)).collect(
			Collectors.toList());
		return memberDtoList;
	}

	private MemberGathering createMemberGathering(MemberGatheringDto memberGatheringDto) {
		MemberGathering memberGathering = new MemberGathering();
		memberGathering.setScore(memberGathering.getScore());
		memberGathering.setScoreYn(memberGathering.getScoreYn());
		memberGathering.setGathering(gatheringRepository.findByGatheringNo(memberGatheringDto.getGatheringNo()));
		memberGathering.setMember(memberRepository.findByUserId(memberGatheringDto.getUserId()));

		return memberGathering;
	}
}
