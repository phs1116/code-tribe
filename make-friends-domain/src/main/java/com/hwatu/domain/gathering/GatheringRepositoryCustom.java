package com.hwatu.domain.gathering;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * Created by hwatu on 2017. 2. 23..
 */
public interface GatheringRepositoryCustom {
	PageImpl findOrderByPopular(Pageable pageable, Long categoryId);
	PageImpl findOrderByGatheringNo(Pageable pageable, Long categoryId);
	PageImpl<Gathering> findOrderByPopularByAttendUserId(Pageable pageable, String userId, Long categoryId);
	PageImpl findOrderByGatheringNoByAttendUserId(Pageable pageable, String userId, Long categoryId);
	PageImpl findOrderByPopularByUserId(Pageable pageable, String userId, Long categoryId);
	PageImpl findOrderByGatheringNoByUserId(Pageable pageable, String userId, Long categoryId);
}
