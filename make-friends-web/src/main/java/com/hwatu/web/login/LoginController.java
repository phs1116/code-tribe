package com.hwatu.web.login;

import com.hwatu.domain.ExceptionMessage;
import com.hwatu.domain.member.LoginFailedException;
import com.hwatu.domain.member.MemberDto;
import com.hwatu.domain.member.MemberService;
import com.hwatu.domain.member.NotIdException;
import com.hwatu.web.interceptor.LoginAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by hwatu on 2017. 2. 24..
 */
@Controller
public class LoginController {

	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login/loginForm";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(value = "returnUrl", required = false) String returnUrl,
		@RequestParam String userId, @RequestParam String password, HttpSession session) throws NotIdException, LoginFailedException {

		MemberDto memberDto = memberService.findByIdAndPassword(userId, password);
		if (memberDto != null) {
			session.setAttribute(LoginAttributes.ID.name(), memberDto.getUserId());
			session.setAttribute(LoginAttributes.NICKNAME.name(), memberDto.getNickname());
		}else{
			throw new LoginFailedException(ExceptionMessage.MEMBER_VALID_ERROR);
		}

		return "redirect:" + (returnUrl == null ? "/gatherings" : returnUrl);
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(LoginAttributes.ID.name());
		session.removeAttribute(LoginAttributes.NICKNAME.name());
		return "redirect:/gatherings";
	}
}
