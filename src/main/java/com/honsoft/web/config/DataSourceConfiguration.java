package com.honsoft.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

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

	@Bean
	public DataSourceInitializer dataSourceInitializer(@Qualifier("datasource") DataSource datasource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("schema-h2.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("data-h2.sql"));

		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(datasource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		return dataSourceInitializer;
	}

	@Bean
	public DataSourceInitializer mysqlDataSourceInitializer(@Qualifier("mysqlDatasource") DataSource datasource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("schema-mysql.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("data-mysql.sql"));

		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(datasource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		return dataSourceInitializer;
	}

}
