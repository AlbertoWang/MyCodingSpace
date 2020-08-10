package com.fvck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplicationController {

	@ResponseBody
	@RequestMapping("/test")
	public String demo() {
		return "32222.html";
	}
}
