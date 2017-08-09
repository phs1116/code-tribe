package com.hwatu.domain.memberGathering;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by hwatu on 2017. 2. 24..
 */
@Repository
public interface MemberGatheringRepository extends JpaRepository<MemberGathering, Long>, QueryDslPredicateExecutor<MemberGathering> {

	//유저의 참가한 모임
	Page<MemberGathering> findByMember_UserId(String userId, Pageable pageable);

	//모임에 참가한 유저
	Page<MemberGathering> findByGathering_GatheringNo(Long gatheringNo, Pageable pageable);

	MemberGathering findByGathering_GatheringNoAndMember_UserId(Long gatheringNo, String userId);
}
