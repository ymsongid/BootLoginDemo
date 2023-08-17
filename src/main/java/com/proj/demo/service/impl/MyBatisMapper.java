package com.proj.demo.service.impl;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyBatisMapper {
	public String time() throws Exception;
}
