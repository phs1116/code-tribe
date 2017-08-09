package com.hwatu.domain.gathering;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hwatu on 2017. 2. 21..
 */
@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long>, QueryDslPredicateExecutor<Gathering>, GatheringRepositoryCustom {
	List<Gathering> findByUserId(String userId);
	Gathering findByGatheringNo(Long gatheringNo);
}
