package com.memo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "com.memo.*")
public class DatabaseConfig {
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		// SqlSessionFactory는 데이터베이스와의 연결과 SQL의 실행에 대한 모든 것을 가진 가장 중요한 객체임
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

		// 커넥션 풀을 구현하기 위한 스펙을 정해놓은 인터페이스 = dataSource
		sessionFactory.setDataSource(dataSource);

		// 쿼리문이 작성되어지는 mapper.xml에서 읽어옴 -> 디렉토리 위치
		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
		sessionFactory.setMapperLocations(res);

		return sessionFactory.getObject();

	}
}
