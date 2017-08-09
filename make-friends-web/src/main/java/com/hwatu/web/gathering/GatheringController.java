package com.hwatu.web.gathering;

import com.hwatu.domain.ExceptionMessage;
import com.hwatu.domain.ResponseDto;
import com.hwatu.domain.gathering.*;
import com.hwatu.domain.memberGathering.*;
import com.hwatu.web.interceptor.AuthCheck;
import com.hwatu.web.interceptor.LoginAttributes;
import com.hwatu.web.interceptor.LoginCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hwatu on 2017. 2. 22..
 */
@Slf4j
@Controller
@RequestMapping(value = "/gatherings")
public class GatheringController {
	private static final String GATHERING_NO = "gatheringNo";
	private static final String POPULAR = "popular";
	@Autowired
	private GatheringService gatheringService;
	@Autowired
	private MemberGatheringService memberGatheringService;

	private static final String USER_ATTEND = "attend";
	private static final String USER_HOST = "host";

	/**
	 * 모임 등록 폼을 출력한다
	 * @return
	 */

	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	@LoginCheck
	public String showRegistForm() {
		return "gathering/registForm";
	}

	/**
	 * 모임을 등록한다
	 * @param gatheringDetailDto
	 * @param session
	 * @return
	 */

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	@LoginCheck
	public String regist(@ModelAttribute("gatheringDetailDto") @Valid GatheringDetailDto gatheringDetailDto, HttpSession session) {
		String userId = (String) session.getAttribute(LoginAttributes.ID.name());
		gatheringDetailDto.setUserId((String) session.getAttribute(LoginAttributes.ID.name()));
		Long gatheringNo = gatheringService.save(gatheringDetailDto);
		return "redirect:/gatherings/" + gatheringNo;
	}

	/**
	 * 모임 수정 폼을 출력한다
	 * @return
	 */

	@RequestMapping(value = "/update/{gatheringNo}", method = RequestMethod.GET)
	@LoginCheck
	public String showUpdateForm(@PathVariable Long gatheringNo, Model model, HttpSession session) throws NotAuthException, NotFoundException {
		String userId = (String) session.getAttribute(LoginAttributes.ID.name());
		GatheringDetailVO gatheringDetailVO = gatheringService.findByGatheringNo(gatheringNo, userId);
		if (gatheringDetailVO.getAttendType() != GatheringVO.TYPE_HOST)
			throw new NotAuthException(ExceptionMessage.NO_PERMISSION);

		model.addAttribute("gatheringInfo", gatheringDetailVO);
		return "gathering/updateForm";
	}

	/**
	 * 모임을 수정한다.
	 * @param gatheringDetailDto
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@LoginCheck
	public String update(@ModelAttribute("gatheringDto") @Valid GatheringDetailDto gatheringDetailDto, HttpSession session) {
		gatheringDetailDto.setUserId((String) session.getAttribute(LoginAttributes.ID.name()));
		Long gatheringNo = gatheringService.save(gatheringDetailDto);
		return "redirect:/gatherings/" + gatheringNo;
	}

	/**
	 * 모임을 삭제한다.
	 * @param gatheringNo
	 * @param session
	 * @return
	 * @throws NotAuthException
	 * @throws NotFoundException
	 */
	@RequestMapping(value = "/delete/{gatheringNo}", method = RequestMethod.GET)
	@LoginCheck
	public String delete(@PathVariable Long gatheringNo, HttpSession session) throws NotAuthException, NotFoundException {
		String userId = (String) session.getAttribute(LoginAttributes.ID.name());
		gatheringService.delete(gatheringNo, userId);
		return "redirect:/gatherings/";
	}

	/**
	 * 모임 상세 정보를 출력한다.
	 * @param gatheringNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{gatheringNo}", method = RequestMethod.GET)
	public String showDetailInfo(@PathVariable Long gatheringNo, Model model, HttpSession session) throws NotFoundException {
		String userId = (String) session.getAttribute(LoginAttributes.ID.name());
		GatheringDetailVO gatheringDetailVO = gatheringService.findByGatheringNo(gatheringNo, userId);
		model.addAttribute("gatheringInfo", gatheringDetailVO);
		return "gathering/gatheringDetail";
	}

	/**
	 * 모임 리스트를 출력한다.
	 * @param page
	 * @param sort
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String findAll(@RequestParam(value = "page", required = false) Integer page,
		@RequestParam(value = "sort", required = false) String sort,
		@RequestParam(value = "category", required = false) Long categoryId,
		Model model, HttpSession session) {

		String userId = (String) session.getAttribute(LoginAttributes.ID.name());
		Integer pageNum = page == null ? 0 : page - 1;
		String sortString = sort == null ? GATHERING_NO : sort;
		GatheringListVO gatheringListVO = null;

		if (sortString.equals(GATHERING_NO))
			gatheringListVO = gatheringService.findAll(userId, pageNum, sortString, categoryId);
		else if (sortString.equals(POPULAR))
			gatheringListVO = gatheringService.findOrderByPopular(userId, pageNum, categoryId);
		model.addAttribute("gatheringList", gatheringListVO);
		return "gathering/mainList";
	}

	/**
	 * 특정 유저의 모임을 출력한다
	 * type=g : 참가 모임, type=h : 개설 모임
	 * @param userId
	 * @param page
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{userId}/{type}", method = RequestMethod.GET)
	@LoginCheck
	@AuthCheck
	public String showMyGathering(@PathVariable String userId,
		@RequestParam(value = "page", required = false) Integer page, @PathVariable String type, Model model) {
		List<GatheringDetailDto> gatheringDetailDtoList = new ArrayList<>();
		if (page == null) {
			page = 0;
		}
		gatheringDetailDtoList = getGatheringDetailDtoList(userId, type, page);
		model.addAttribute("gatheringList", gatheringDetailDtoList);
		return "gathering/mainList";
	}

	/**
	 * 모임을 참가한다.
	 * @param gatheringNo
	 * @param session˜
	 * @return
	 */
	@RequestMapping(value = "/attend/{gatheringNo}", method = RequestMethod.GET)
	@LoginCheck
	@ResponseBody
	public ResponseDto<AttendInfoDto> attendGathering(@PathVariable Long gatheringNo, HttpSession session) throws AlreadyAttendedException {
		MemberGatheringDto memberGatheringDto = getMemberGatheringDto(gatheringNo, session);
		AttendInfoDto attendInfoDto = memberGatheringService.save(memberGatheringDto);
		ResponseDto<AttendInfoDto> responseDto = new ResponseDto<>(true, null, attendInfoDto);

		return responseDto;
	}

	/**
	 * 모임 참가를 취소한다.
	 * @param gatheringNo
	 * @param session˜
	 * @return
	 */
	@RequestMapping(value = "/cancel/{gatheringNo}", method = RequestMethod.GET)
	@LoginCheck
	@ResponseBody
	public ResponseDto<AttendInfoDto> cancelAttendGathering(@PathVariable Long gatheringNo, HttpSession session) throws NotAttendedException {
		MemberGatheringDto memberGatheringDto = getMemberGatheringDto(gatheringNo, session);
		AttendInfoDto attendInfoDto = memberGatheringService.delete(memberGatheringDto);
		ResponseDto<AttendInfoDto> responseDto = new ResponseDto<>(true, null, attendInfoDto);

		return responseDto;
	}
	/**
	 * 모임을 종료한다.
	 * @param gatheringNo
	 * @param session˜
	 * @return
	 */
	@RequestMapping(value = "/exit/{gatheringNo}", method = RequestMethod.GET)
	@LoginCheck
	public String exitGathering(@PathVariable Long gatheringNo, HttpSession session)
		throws NotFoundException, NotAuthException {
		String userId = (String) session.getAttribute(LoginAttributes.ID.name());
		gatheringService.exitGathering(gatheringNo, userId);
		return "redirect:/gatherings/"+gatheringNo;
	}
	/**
	 * MemberGatheringDto를 생성한다.
	 * @param gatheringNo
	 * @param session
	 * @return
	 */
	private MemberGatheringDto getMemberGatheringDto(@PathVariable Long gatheringNo, HttpSession session) {
		MemberGatheringDto memberGatheringDto = new MemberGatheringDto();
		memberGatheringDto.setUserId((String) session.getAttribute(LoginAttributes.ID.name()));
		memberGatheringDto.setGatheringNo(gatheringNo);
		return memberGatheringDto;
	}

	/**
	 * getGatheringDetailDtoList를 생성한다.
	 * @param userId
	 * @param type
	 * @param page
	 * @return
	 */
	private List<GatheringDetailDto> getGatheringDetailDtoList(String userId, String type, Integer page) {
		if (type == null || type.equals(USER_ATTEND)) {
			return memberGatheringService.findByUserId(userId, page);
		} else if (type.equals(USER_HOST)) {
			return gatheringService.findByUserId(userId);
		} else
			return null;
	}
}
