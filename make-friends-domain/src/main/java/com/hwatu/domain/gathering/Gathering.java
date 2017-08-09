package com.hwatu.domain.gathering;

import com.hwatu.domain.category.Category;
import com.hwatu.domain.memberGathering.MemberGathering;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hwatu on 2017. 2. 21..
 */

@Data
@Entity
@Table(name = "gatherings")
public class Gathering {
	@Id
	@Column(name = "gatheringNo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gatheringNo;

	@Column(name = "userId", nullable = false, length = 50)
	private String userId;

	@Column(name = "gatheringTitle", nullable = false, length = 128)
	private String gatheringTitle;

	@Column(name = "content")
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gatheringAt")
	private Date gatheringAt;

	@Column(name = "gatheringPlace", length = 64)
	private String gatheringPlace;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "exitYn")
	private boolean exitYn;


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryId")
	private Category category;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt", nullable = false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedAt", nullable = false)
	private Date updatedAt;

	@Column(name ="createdBy", length = 50)
	private String createdBy;

	@Column(name = "updatedBy", length = 50)
	private String updatedBy;

	@OneToMany(mappedBy = "gathering", fetch = FetchType.EAGER)
	private List<MemberGathering> memberGatherings = new ArrayList<>();

	@PrePersist
	void onCreate(){
		Date now = new Date();
		createdAt = now;
		updatedAt = now;
		exitYn = false;
	}

	@PreUpdate
	void onUpdate(){
		updatedAt = new Date();
	}


}
