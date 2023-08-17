package com.proj.demo.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.proj.demo.service.MyBatisService;

@Controller
public class MyBatisController {

	@Resource
	MyBatisService MyBatisService;
	
	@GetMapping("/")
	public String greeting() throws Exception {
		String time = MyBatisService.time();
		System.out.println(time + "-home open");
		return "home";
	}

	@GetMapping("/login")
	public String login2() throws Exception {
		String time = MyBatisService.time();
		System.out.println(time + "-login open");
		return "login";
	}
}
