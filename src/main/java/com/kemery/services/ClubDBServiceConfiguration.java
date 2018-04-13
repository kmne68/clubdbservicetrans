package com.kemery.services;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kemery.ClubDBConfiguration;
import com.kemery.MemberDao;
//import com.kemery.services.MemberServiceImpl;

@Configuration
@EnableTransactionManagement
@Import(ClubDBConfiguration.class)
public class ClubDBServiceConfiguration {

	@Bean
	public DataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/club296?useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		
		return dataSource;
	}
	
	
	@Bean
	@Autowired
	public MemberService memberService(MemberDao memberDao) {
		
		MemberServiceImpl bean = new MemberServiceImpl();
		
		bean.setMemberDao(memberDao);
		
		return bean;
		
	}
	
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}
	
}
