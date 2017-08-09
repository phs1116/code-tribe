package com.hwatu.domain.gathering;

import com.hwatu.domain.member.MemberVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hwatu on 2017. 3. 7..
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GatheringDetailVO extends GatheringVO {

	private String content;

	private Double latitude;

	private Double longitude;

	private List<MemberVO> memberList;

	public GatheringDetailVO(Gathering gathering, String userId) {
		super(gathering, userId);
		content = gathering.getContent();
		latitude = gathering.getLatitude();
		longitude = gathering.getLongitude();
		memberList = gathering.getMemberGatherings().stream().map(memberGathering -> new MemberVO(memberGathering.getMember())).collect(
			Collectors.toList());
		memberList = memberList.size() == 0 ? null : memberList;
	}

	public static GatheringDetailVO newInstance(Gathering gathering, String userId){
		return new GatheringDetailVO(gathering, userId);
	}
}
