package com.hwatu.web;

import com.hwatu.domain.ResponseDto;
import com.hwatu.domain.memberGathering.AlreadyAttendedException;
import com.hwatu.domain.memberGathering.NotAttendedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hwatu on 2017. 3. 9..
 */

@ControllerAdvice
public class WebExceptionHandleControllerAdvice {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView foundException(Exception e) {
		String errorMessage = e.getMessage();
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", errorMessage);
		mav.setViewName("error/error");
		return mav;

	}

	@ExceptionHandler({ AlreadyAttendedException.class, NotAttendedException.class })
	@ResponseBody
	public ResponseDto apiException(Exception e) {
		return new ResponseDto<>(false, e.getMessage(), null);
	}

	public boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

}
