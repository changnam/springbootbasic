package com.honsoft.web.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(value = { "com.honsoft.web.mapper.h2" }, sqlSessionFactoryRef = "h2SqlSessionFactory")
@EnableJpaRepositories(basePackages = "com.honsoft.web.repository.h2", entityManagerFactoryRef = "h2EntityManagerFactory", transactionManagerRef = "h2TransactionManager")
public class H2DataSourceConfig {
	//private static final String DEFAULT_NAMING_STRATEGY = "org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy";
	
	@Autowired
	private Environment env;
	
	// datasource
	@Bean(name = "h2DataSource", destroyMethod = "close")
	@ConfigurationProperties(prefix = "h2.datasource.hikari")
	@Primary
	public DataSource h2DataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(@Qualifier("h2DataSource") DataSource datasource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/h2/schema-h2.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/h2/data-h2.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/common/user-data.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/common/order-data.sql"));

		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(datasource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		dataSourceInitializer.setEnabled(env.getProperty("h2.datasource.initialize", Boolean.class, false));
         
		return dataSourceInitializer;
	}

	@Bean(name = "h2TransactionManager")
	@Primary
    public PlatformTransactionManager h2TransactionManager(@Qualifier("h2EntityManagerFactory")EntityManagerFactory entityManagerFactory)
    {
        return new JpaTransactionManager(entityManagerFactory);
    }
	
	// mybatis
	@Bean(name = "h2SqlSessionFactory")
	@Primary
	public SqlSessionFactory h2SqlSessionFactory(@Qualifier("h2DataSource") DataSource h2DataSource,
			ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(h2DataSource);
		sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.honsoft.web.dto");
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		// sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:sql/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "h2SqlSessiontemplate")
	@Primary
	public SqlSessionTemplate h2SqlSessionTemplate(SqlSessionFactory h2SqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(h2SqlSessionFactory);
	}

	// jpa
	@PersistenceContext(unitName = "h2Unit")
	@Primary
	@Bean(name = "h2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean securityEntityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(h2DataSource());
        factory.setPackagesToScan(new String[]{"com.honsoft.web.entity"});
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
     
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
        factory.setJpaProperties(jpaProperties);
     
        return factory;
    }

	@Bean //lazy initiailization , case user entity attribute address
    public OpenEntityManagerInViewFilter h2OpenEntityManagerInViewFilter()
    {
        OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
        osivFilter.setEntityManagerFactoryBeanName("h2EntityManagerFactory");
        return osivFilter;
    }

    
}
