package com.honsoft.web.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration {

	@Bean
	@Primary
	@ConfigurationProperties("h2.datasource.hikari")
	public DataSource datasource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	@ConfigurationProperties("mysql.datasource.hikari")
	public DataSource mysqlDatasource() {
		return DataSourceBuilder.create().build();
	}
}
