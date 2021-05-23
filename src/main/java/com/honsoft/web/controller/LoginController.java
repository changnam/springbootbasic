package com.honsoft.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.honsoft.web.entity.User;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String login(Model m) {
		m.addAttribute("message", "Hello spring");
		return "thymeleaf/login";
	}
	
	
	@GetMapping("/login-error")
	public String loginError(Model m) {
		m.addAttribute("message", "Hello spring");
		return "thymeleaf/login";
	}
	
	
	@ModelAttribute("user")
    public User userDto() {
        return new User();
    }

    @GetMapping("/signup")
    public String showForm() {
        return "thymeleaf/signup";
    }

    @PostMapping("/signup")
    public String submitForm(@Validated @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "thymeleaf/signup";
        }
        return "thymeleaf/success";
    }
    
}
