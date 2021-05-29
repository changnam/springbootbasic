package com.honsoft.web.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.honsoft.web.filter.MyFilter;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/jsp/", ".jsp");
	}

	// Register Filter
	@Bean
	public FilterRegistrationBean<MyFilter> myFilter() {
		FilterRegistrationBean<MyFilter> filter = new FilterRegistrationBean<>(new MyFilter());
		filter.addUrlPatterns("/*");
		filter.setOrder(3);
		// Mapping filter to a Servlet
		// bean.addServletRegistrationBeans(new ServletRegistrationBean[] {
		// servletRegistrationBean()
		// });
		return filter;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/home").setViewName("thymeleaf/home");
	}
    

}