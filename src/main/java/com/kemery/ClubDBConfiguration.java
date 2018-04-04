package com.kemery;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.object.MappingSqlQuery;

@Configuration
public class ClubDBConfiguration {

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
	public JdbcTemplate jdbcTemplate() {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		
		jdbcTemplate.setDataSource(dataSource());
		
		return jdbcTemplate;
	}
	
	@Bean
	public MemberDao memberDao() {
		
		MemberDaoJdbcImpl memberDao = new MemberDaoJdbcImpl();
		
		memberDao.setJdbcTemplate(jdbcTemplate());
		memberDao.setMemberByIdQuery(memberByIdQuery());
		return memberDao;
	}
	
	@Bean
	public MappingSqlQuery<Member> memberByIdQuery() {
		MemberByIdQuery query = new MemberByIdQuery(dataSource());
		return query;
	}
	
}

