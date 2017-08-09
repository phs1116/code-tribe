package com.hwatu.domain.gathering;

import com.hwatu.domain.ExceptionMessage;
import com.hwatu.domain.category.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hwatu on 2017. 2. 22..
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class GatheringServiceImpl implements GatheringService {
	private static final int PAGE_SIZE = 5;
	@Autowired
	private GatheringRepository gatheringRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional(readOnly = false)
	@Override
	public Long save(GatheringDetailDto gatheringDetailDto) {
		Gathering gathering = modelMapper.map(gatheringDetailDto, Gathering.class);
		gathering.setCategory(categoryRepository.findByCategoryId(gatheringDetailDto.getCategoryId()));
		Gathering result = gatheringRepository.save(gathering);
		return result.getGatheringNo();

	}

	@Override
	public GatheringDetailVO findByGatheringNo(Long gatheringNo, String userId) throws NotFoundException {
		Gathering gathering = gatheringRepository.findByGatheringNo(gatheringNo);
		if (gathering == null)
			throw new NotFoundException(ExceptionMessage.NOT_FOUND_GATHERING);
		GatheringDetailVO gatheringDetailVO = new GatheringDetailVO(gathering, userId);
		return gatheringDetailVO;
	}

	@Override
	public GatheringListVO findAll(String userId, Integer page, String sort, Long categoryId) {
		Pageable pageable = new PageRequest(page, PAGE_SIZE, new Sort(Sort.Direction.DESC, sort));
		Page<Gathering> gatheringPage = gatheringRepository.findOrderByGatheringNo(pageable, categoryId);
		return getGatheringListVO(userId, page, gatheringPage);
	}

	@Override
	public GatheringListVO findOrderByPopular(String userId, Integer page, Long categoryId) {
		Pageable pageable = new PageRequest(page, PAGE_SIZE, null);
		Page<Gathering> gatheringPage = gatheringRepository.findOrderByPopular(pageable, categoryId);
		return getGatheringListVO(userId, page, gatheringPage);
	}

	@Override
	public List<GatheringDetailDto> findByUserId(String userId) {
		List<Gathering> gatheringList = gatheringRepository.findByUserId(userId);
		return gatheringList.stream().map((g) -> modelMapper.map(g, GatheringDetailDto.class)).collect(Collectors.toList());
	}

	@Override
	public GatheringListVO findOrderByPopularByAttendUserId(String userId, Integer page, Long categoryId) {
		Pageable pageable = new PageRequest(page, PAGE_SIZE, null);
		Page<Gathering> gatheringPage = gatheringRepository.findOrderByPopularByAttendUserId(pageable, userId, categoryId);
		return getGatheringListVO(userId, page, gatheringPage);
	}

	@Override
	public GatheringListVO findOrderByGatheringNoByAttendUserId(String userId, Integer page, Long categoryId) {
		Pageable pageable = new PageRequest(page, PAGE_SIZE, null);
		Page<Gathering> gatheringPage = gatheringRepository.findOrderByGatheringNoByAttendUserId(pageable, userId, categoryId);
		return getGatheringListVO(userId, page, gatheringPage);
	}

	@Override
	public GatheringListVO findOrderByPopularByUserId(String userId, Integer page, Long categoryId) {
		Pageable pageable = new PageRequest(page, PAGE_SIZE, null);
		Page<Gathering> gatheringPage = gatheringRepository.findOrderByPopularByUserId(pageable, userId, categoryId);
		return getGatheringListVO(userId, page, gatheringPage);
	}

	@Override
	public GatheringListVO findOrderByGatheringNoByUserId(String userId, Integer page, Long categoryId) {
		Pageable pageable = new PageRequest(page, PAGE_SIZE, null);
		Page<Gathering> gatheringPage = gatheringRepository.findOrderByGatheringNoByUserId(pageable, userId, categoryId);
		return getGatheringListVO(userId, page, gatheringPage);
	}

	@Override
	public void delete(Long gatheringNo, String userId) throws NotFoundException, NotAuthException {
		Gathering gathering = gatheringRepository.findByGatheringNo(gatheringNo);
		if (gathering == null)
			throw new NotFoundException(ExceptionMessage.NOT_FOUND_GATHERING);
		if (!gathering.getUserId().equals(userId))
			throw new NotAuthException(ExceptionMessage.NO_PERMISSION);
		gatheringRepository.delete(gathering);
	}

	@Override
	@Transactional(readOnly = false)
	public void exitGathering(Long gatheringNo, String userId) throws NotFoundException, NotAuthException {
		Gathering gathering = gatheringRepository.findByGatheringNo(gatheringNo);
		if (gathering == null)
			throw new NotFoundException(ExceptionMessage.NOT_FOUND_GATHERING);
		if (!gathering.getUserId().equals(userId))
			throw new NotAuthException(ExceptionMessage.NO_PERMISSION);

		gathering.setExitYn(true);
		gatheringRepository.save(gathering);
	}

	private GatheringListVO getGatheringListVO(String userId, Integer page, Page<Gathering> gatheringPage) {
		List<GatheringVO> gatheringListViewDtoList = gatheringPage.getContent().stream().map(
			(g) -> new GatheringVO(g, userId)).collect(
			Collectors.toList());
		return new GatheringListVO(page, gatheringPage.getTotalPages(), gatheringListViewDtoList);
	}
}
