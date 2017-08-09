package com.hwatu.web.member;

import com.hwatu.domain.ExceptionMessage;
import com.hwatu.domain.gathering.GatheringListVO;
import com.hwatu.domain.gathering.GatheringService;
import com.hwatu.domain.member.MemberDto;
import com.hwatu.domain.member.MemberService;
import com.hwatu.domain.member.NotIdException;
import com.hwatu.domain.member.NotMatchedPasswordException;
import com.hwatu.web.interceptor.AuthCheck;
import com.hwatu.web.interceptor.LoginCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by hwatu on 2017. 2. 22..
 */

@Controller
@RequestMapping("/members")
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private GatheringService gatheringService;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String joinForm() {
		return "member/joinForm";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute MemberDto memberDto) throws NotMatchedPasswordException, NotIdException {
		if (memberDto.getUserId() == null)
			throw new NotIdException(ExceptionMessage.NOT_INPUT_ID);
		memberService.save(memberDto);
		return "redirect:/gatherings";
	}

	@RequestMapping(value = "/update/{userId}", method = RequestMethod.GET)
	@LoginCheck
	@AuthCheck
	public String updateForm(@PathVariable String userId, Model model, HttpSession session) throws Exception {
		setMemberDtoToModel(userId, model);
		return "member/updateForm";
	}

	@RequestMapping(value = { "/{userId}", "/{userId}/attend" }, method = RequestMethod.GET)
	@LoginCheck
	@AuthCheck
	public String showDetailMemberInfoAttend(@PathVariable String userId, @RequestParam(value = "sort", required = false) String sort,
		@RequestParam(value = "page", required = false) Integer page,
		@RequestParam(value = "category", required = false) Long categoryId, Model model) {
		setMemberDtoToModel(userId, model);
		Integer pageNum = page == null ? 0 : page - 1;
		String sortString = sort == null ? "gatheringNo" : sort;
		GatheringListVO gatheringListVO = null;

		if (sortString.equals("gatheringNo"))
			gatheringListVO = gatheringService.findOrderByGatheringNoByAttendUserId(userId, pageNum, categoryId);
		else if (sortString.equals("popular"))
			gatheringListVO = gatheringService.findOrderByPopularByAttendUserId(userId, pageNum, categoryId);
		model.addAttribute("gatheringList", gatheringListVO);

		return "member/info";
	}

	//TODO 두개 메서드 합치기
	@RequestMapping(value = "/{userId}/host", method = RequestMethod.GET)
	@LoginCheck
	@AuthCheck
	public String showDetailMemberInfoHost(@PathVariable String userId, @RequestParam(value = "sort", required = false) String sort,
		@RequestParam(value = "page", required = false) Integer page,
		@RequestParam(value = "category", required = false) Long categoryId, Model model) {
		setMemberDtoToModel(userId, model);

		Integer pageNum = page == null ? 0 : page - 1;
		String sortString = sort == null ? "gatheringNo" : sort;
		GatheringListVO gatheringListVO = null;

		if (sortString.equals("gatheringNo"))
			gatheringListVO = gatheringService.findOrderByGatheringNoByUserId(userId, pageNum, categoryId);
		else if (sortString.equals("popular"))
			gatheringListVO = gatheringService.findOrderByPopularByUserId(userId, pageNum, categoryId);
		model.addAttribute("gatheringList", gatheringListVO);
		return "member/info";
	}

	private void setMemberDtoToModel(@PathVariable String userId, Model model) {
		MemberDto memberDto = memberService.findById(userId);
		model.addAttribute("memberInfo", memberDto);
	}

}
