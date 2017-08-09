package com.hwatu.domain.gathering;

import lombok.Data;

import java.util.Date;

/**
 * Created by hwatu on 2017. 3. 3..
 */
@Data
public class GatheringVO {
	public static final Integer TYPE_HOST = 3;
	public static final Integer TYPE_GUEST = 2;
	public static final Integer TYPE_NOTATTEND = 1;

	private Long gatheringNo;

	private Long categoryId;

	private String categoryName;

	private String userId;

	private String gatheringTitle;

	private Date gatheringAt;

	private String gatheringPlace;

	private Integer attendNo;

	private boolean exitYn;

	private Integer attendType;

	public GatheringVO(Gathering gathering, String userId) {
		this.gatheringNo = gathering.getGatheringNo();
		this.categoryId = gathering.getCategory().getCategoryId();
		this.categoryName = gathering.getCategory().getCategoryName();
		this.userId = gathering.getUserId();
		this.gatheringTitle = gathering.getGatheringTitle();
		this.gatheringAt = gathering.getGatheringAt();
		this.gatheringPlace = gathering.getGatheringPlace();
		this.attendNo = gathering.getMemberGatherings().size();
		this.exitYn = gathering.isExitYn();
		if (gathering.getUserId().equals(userId))
			this.attendType = 3;
		else if (gathering.getMemberGatherings().stream().anyMatch(mg -> mg.getMember().getUserId().equals(userId)))
			this.attendType = 2;
		else
			this.attendType = 1;
	}
}
