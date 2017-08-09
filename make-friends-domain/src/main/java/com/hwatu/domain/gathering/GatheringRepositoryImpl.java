package com.hwatu.domain.gathering;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

/**
 * Created by hwatu on 2017. 2. 23..
 */

public class GatheringRepositoryImpl extends QueryDslRepositorySupport implements GatheringRepositoryCustom {
	public GatheringRepositoryImpl() {
		super(GatheringRepository.class);
	}

	public static final Long CATEGORY_ALL = new Long(0);

	/**
	 * 전체 인기순 조회
	 * @param pageable
	 * @param categoryId
	 * @return
	 */
	@Override
	public PageImpl findOrderByPopular(Pageable pageable, Long categoryId) {
		QGathering gathering = QGathering.gathering;
		JPQLQuery query = from(gathering).orderBy(gathering.memberGatherings.size().desc());
		return getGatheringsList(pageable, categoryId, gathering, query);
	}

	/**
	 * 전체 아이디순 조회
	 * @param pageable
	 * @param categoryId
	 * @return
	 */
	@Override
	public PageImpl findOrderByGatheringNo(Pageable pageable, Long categoryId) {
		QGathering gathering = QGathering.gathering;
		JPQLQuery query = from(gathering).orderBy(gathering.gatheringNo.desc());
		return getGatheringsList(pageable, categoryId, gathering, query);
	}

	/**
	 * 유저가 참여한 인기순 조회
	 * @param pageable
	 * @param userId
	 * @param categoryId
	 * @return
	 */
	@Override
	public PageImpl<Gathering> findOrderByPopularByAttendUserId(Pageable pageable, String userId, Long categoryId) {
		QGathering gathering = QGathering.gathering;
		JPQLQuery query = from(gathering).orderBy(gathering.memberGatherings.size().desc()).where(
			gathering.memberGatherings.any().member.userId.eq(userId));
		return getGatheringsList(pageable, categoryId, gathering, query);
	}

	/**
	 * 유저가 참여한 아이디순 조회
	 * @param pageable
	 * @param userId
	 * @param categoryId
	 * @return
	 */
	@Override
	public PageImpl findOrderByGatheringNoByAttendUserId(Pageable pageable, String userId, Long categoryId) {
		QGathering gathering = QGathering.gathering;
		JPQLQuery query = from(gathering).orderBy(gathering.gatheringNo.desc()).where(gathering.memberGatherings.any().member.userId.eq(userId));
		return getGatheringsList(pageable, categoryId, gathering, query);
	}

	@Override
	public PageImpl findOrderByPopularByUserId(Pageable pageable, String userId, Long categoryId) {
		QGathering gathering = QGathering.gathering;
		JPQLQuery query = from(gathering).orderBy(gathering.memberGatherings.size().desc()).where(gathering.userId.eq(userId));
		return getGatheringsList(pageable, categoryId, gathering, query);
	}

	@Override
	public PageImpl findOrderByGatheringNoByUserId(Pageable pageable, String userId, Long categoryId) {
		QGathering gathering = QGathering.gathering;
		JPQLQuery query = from(gathering).orderBy(gathering.gatheringNo.desc()).where(gathering.userId.eq(userId));
		return getGatheringsList(pageable, categoryId, gathering, query);
	}

	/**
	 * Page 객체 생성
	 * @param pageable
	 * @param categoryId
	 * @param gathering
	 * @param query
	 * @return
	 */
	private PageImpl<Gathering> getGatheringsList(Pageable pageable, Long categoryId, QGathering gathering, JPQLQuery<Gathering> query) {
		if (categoryId != null && !categoryId.equals(CATEGORY_ALL)) {
			query = query.where(gathering.category.categoryId.eq(categoryId));
		}
		QueryResults<Gathering> searchResults = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
		return new PageImpl<Gathering>(searchResults.getResults(), pageable, searchResults.getTotal());
	}
}
