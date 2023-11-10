package com.hk.calboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@GetMapping(value = "/")
	public String home() {
		System.out.println("home");
		return "thymeleaf/home";
	}
}