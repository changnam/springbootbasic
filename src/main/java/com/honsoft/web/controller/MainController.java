package com.honsoft.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/homejsp")
	public String homejsp(Model m) {
		m.addAttribute("message", "Hello spring");
		return "home";
	}
		
	@GetMapping("/user")
	public String user(Model m) {
		m.addAttribute("message", "Hello spring");
		return "user";
	}
	
	

	@GetMapping("/user/index")
	public String userIndex(Model m) {
		m.addAttribute("message", "Hello spring");
		return "thymeleaf/home";
	}
	

}
