package com.hwatu.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hwatu on 2017. 8. 8..
 */

//health check
@RestController
public class HelloController {
	@RequestMapping("/hello")
	public String test(){
		return "hello";
	}
}
