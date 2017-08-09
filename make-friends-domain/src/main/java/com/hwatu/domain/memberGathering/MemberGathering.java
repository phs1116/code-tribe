package com.hwatu.domain.memberGathering;

import com.hwatu.domain.gathering.Gathering;
import com.hwatu.domain.member.Member;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hwatu on 2017. 2. 23..
 */
@Data
@Entity
@Table(name = "memberGatherings")
public class MemberGathering {
	@Id
	@Column(name = "memberGatheringId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberGatheringId;

	@ManyToOne
	@JoinColumn(name = "gatheringNo")
	private Gathering gathering;

	@ManyToOne
	@JoinColumn(name = "userId")
	private Member member;

	@Column(name = "scoreYn")
	private Boolean scoreYn;

	@Column(name = "score")
	private Integer score;

	@Column(name = "joinAt")
	private Date joinAt;

	@PrePersist
	private void onCreate(){
		joinAt = new Date();
		scoreYn = false;
	}
}
