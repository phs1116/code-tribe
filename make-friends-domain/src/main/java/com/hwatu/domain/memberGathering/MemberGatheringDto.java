package com.hwatu.domain.memberGathering;

import lombok.Data;

import java.util.Date;

/**
 * Created by hwatu on 2017. 2. 25..
 */
@Data
public class MemberGatheringDto {

	private Long gatheringNo;

	private String userId;

	private Boolean scoreYn;

	private Integer score;

	private Date joinAt;
}
