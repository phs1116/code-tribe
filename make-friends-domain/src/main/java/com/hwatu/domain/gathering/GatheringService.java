package com.hwatu.domain.gathering;

import java.util.List;

/**
 * Created by hwatu on 2017. 2. 22..
 */
public interface GatheringService {
	Long save(GatheringDetailDto gatheringDetailDto);
	GatheringDetailVO findByGatheringNo(Long gatheringNo, String userId) throws NotFoundException;
	GatheringListVO findAll(String userId, Integer page, String sort, Long categoryId);
	GatheringListVO findOrderByPopular(String userId, Integer page, Long categoryId);
	//GatheringListVO findByCategory(Long categoryId);
	List<GatheringDetailDto> findByUserId(String userId);
	GatheringListVO findOrderByPopularByAttendUserId(String userId, Integer page, Long categoryId);
	GatheringListVO findOrderByGatheringNoByAttendUserId(String userId, Integer page, Long categoryId);
	GatheringListVO findOrderByPopularByUserId(String userId, Integer page, Long categoryId);
	GatheringListVO findOrderByGatheringNoByUserId(String userId, Integer page, Long categoryId);
	void delete(Long gatheringNo, String userId) throws NotFoundException, NotAuthException;
	void exitGathering(Long gatheringNo, String userId) throws NotFoundException, NotAuthException;
}
