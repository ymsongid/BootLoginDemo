package com.proj.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class SqlSessionConfig {

	@Bean(name = "sqlSession")
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);

		Resource configLocation = new PathMatchingResourcePatternResolver()
				.getResource("classpath:sqlmapper/config/mapper-config.xml");

		Resource[] mapperLocations = new PathMatchingResourcePatternResolver()
				.getResources("classpath:sqlmapper/oracle/**/*.xml");

	//	sqlSessionFactory.setTypeAliasesPackage("com.proj");
		sqlSessionFactory.setConfigLocation(configLocation);
		sqlSessionFactory.setMapperLocations(mapperLocations);

		return sqlSessionFactory.getObject();
	}

	@Bean
	public SqlSessionTemplate egovSqlSessionTemplate(@Qualifier("sqlSession") SqlSessionFactory sqlSession) {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSession);
		return sqlSessionTemplate;
	}
}
