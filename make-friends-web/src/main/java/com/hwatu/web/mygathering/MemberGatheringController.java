package com.hwatu.web.mygathering;

import com.hwatu.domain.gathering.GatheringDetailDto;
import com.hwatu.domain.memberGathering.MemberGatheringService;
import com.hwatu.web.interceptor.AuthCheck;
import com.hwatu.web.interceptor.LoginCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by hwatu on 2017. 2. 27..
 */
@Controller
@RequestMapping(value = "/gathering")
public class MemberGatheringController {
	@Autowired
	private MemberGatheringService memberGatheringService;

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@LoginCheck
	@AuthCheck
	public String showMyGathering(@PathVariable String userId,
		@RequestParam(value = "page", required = false) Integer page, Model model) {
		if(page == null) page = 0;
		List<GatheringDetailDto> gatheringDetailDtoList = memberGatheringService.findByUserId(userId,page);
		model.addAttribute("gatheringList", gatheringDetailDtoList);
		return "gathering/gatheringList";

	}


}
