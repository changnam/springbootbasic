package com.honsoft.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//@Configuration
//@EnableWebMvc
public class MyWebConfig {     
      //@Bean
	  public ViewResolver customViewResolver() {
	      InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
	      internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
	      internalResourceViewResolver.setSuffix(".jsp");
	      internalResourceViewResolver.setOrder(0);
	      return internalResourceViewResolver;
	    }
  }