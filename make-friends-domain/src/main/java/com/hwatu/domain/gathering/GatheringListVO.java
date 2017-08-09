package com.hwatu.domain.gathering;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hwatu on 2017. 2. 23..
 */
@Data
public class GatheringListVO {
	private static final int VIEW_PAGE_NO = 9;
	private int currentPage;
	private int totalPages;
	private int startPageNo;
	private int endPageNo;
	private List<GatheringVO> gatherings;
	private List<Integer> pageNumbers;
	public GatheringListVO(int page, int totalPages, List<GatheringVO> gatheringListViewDtos) {
		this.currentPage = page;
		this.totalPages = totalPages;
		this.gatherings = gatheringListViewDtos;
		this.startPageNo = Math.floorDiv(currentPage, VIEW_PAGE_NO) * VIEW_PAGE_NO + 1;
		this.endPageNo = (startPageNo+VIEW_PAGE_NO > totalPages) ? totalPages : startPageNo+VIEW_PAGE_NO;
		this.pageNumbers = new ArrayList<>();
		for (int i = startPageNo; i <= endPageNo; i++) {
			pageNumbers.add(i);
		}
	}
}
