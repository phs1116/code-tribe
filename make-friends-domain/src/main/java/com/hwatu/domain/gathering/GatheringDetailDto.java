package com.hwatu.domain.gathering;

import com.hwatu.domain.memberGathering.MemberGathering;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hwatu on 2017. 2. 22..
 */
@Data
public class GatheringDetailDto {
	private Long gatheringNo;

	private String userId;

	private String gatheringTitle;

	private String content;

	private Date gatheringAt;

	private String gatheringPlace;

	private Double latitude;

	private Double longitude;

	private boolean exitYn;

	private Long categoryId;

	private List<MemberGathering> memberGatherings = new ArrayList<>();

}
