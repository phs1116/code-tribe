package com.hwatu.domain.member;

import com.hwatu.domain.memberGathering.MemberGathering;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hwatu on 2017. 2. 20..
 */

@Data
@Entity
@Table(name = "members")
public class Member {
	@Id
	@Column(name = "userId", nullable = false, length = 50)
	private String userId;

	@Column(name = "email", nullable = false, length = 50)
	private String email;

	@Column(name = "password", nullable = false, length = 64)
	private String password;

	@Column(name = "nickname", nullable = false, length = 50)
	private String nickname;

	@Column(name = "phoneNumber", length = 20)
	private String phoneNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt", nullable = false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedAt", nullable = false)
	private Date updatedAt;

	@Column(name = "createdBy", length = 50)
	private String createdBy;

	@Column(name = "updatedBy", length = 50)
	private String updatedBy;

	@OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
	private List<MemberGathering> memberGatherings = new ArrayList<>();

	public Member() {
	}

	@PrePersist
	void onCreate() {
		Date now = new Date();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = new Date();
	}

	public void updateMemberInfo(MemberDto memberDto) {
		String newPassword = memberDto.getNewPassword();
		this.password = newPassword != null && !newPassword.equals("")? memberDto.getNewPassword() : memberDto.getPassword();
		this.email = memberDto.getEmail();
		this.nickname = memberDto.getNickname();
		this.phoneNumber = memberDto.getPhoneNumber();
	}

	public boolean matchPassword(String password){
		if(password == null) return false;
		return this.password.equals(password);
	}

}
