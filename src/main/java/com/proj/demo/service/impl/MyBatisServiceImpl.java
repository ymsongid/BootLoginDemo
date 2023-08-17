package com.proj.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.demo.service.MyBatisService;

@Service(value = "MyBatisService")
public class MyBatisServiceImpl implements MyBatisService {

	@Autowired
	MyBatisMapper mapper;

	@Override
	public String time() throws Exception {
		return mapper.time();
	}

}
