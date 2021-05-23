package com.honsoft.web.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(
		value={"com.honsoft.web.mapper.h2"},
		sqlSessionFactoryRef = "h2SqlSessionFactory"
		)
@EnableTransactionManagement
public class H2DataSourceConfig {
	
	@Bean(name="h2DataSource", destroyMethod = "close")
	@ConfigurationProperties(prefix="h2.datasource.hikari")
	@Primary
	public DataSource h2DataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Bean(name="h2SqlSessionFactory")
	@Primary
	public SqlSessionFactory h2SessionFactory(@Qualifier("h2DataSource") DataSource h2DataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(h2DataSource);
		sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.honsoft.web.dto");
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		//sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:sql/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	@Bean(name="h2SqlSessiontemplate")
	@Primary
	public SqlSessionTemplate h2SqlSessionTemplate(SqlSessionFactory h2SqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(h2SqlSessionFactory);
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(@Qualifier("h2DataSource") DataSource datasource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("schema-h2.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("data-h2.sql"));

		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(datasource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		return dataSourceInitializer;
	}
}
