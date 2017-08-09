package com.hwatu.domain.memberGathering;

import com.hwatu.domain.gathering.GatheringDetailDto;
import com.hwatu.domain.member.MemberDto;

import java.util.List;

/**
 * Created by hwatu on 2017. 2. 24..
 */
public interface MemberGatheringService {
	AttendInfoDto save(MemberGatheringDto memberGatheringDto) throws AlreadyAttendedException;
	AttendInfoDto delete(MemberGatheringDto memberGatheringDto) throws NotAttendedException;

	//유저의 참가한 모임
	List<GatheringDetailDto> findByUserId(String userId, Integer page);

	//모임에 참가한 유저
	List<MemberDto> findByGatheringNo(Long gatheringNo, Integer page);
}
