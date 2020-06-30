package com.amane.service;

import com.amane.dto.Execution;
import com.amane.entity.Mark;
import com.amane.entity.Test;

/*
 * 业务接口：站在"使用者"角度设计接口 三个方面：方法定义粒度，参数，返回类型（return 类型/异常）
 */

public interface TestService {
	/**
	 * 
	 * 
	 * @return Map<Integer, String>
	 * @throws Exception 
	 */

	Execution<Integer> register(Test test) throws Exception;

	Execution<Integer> registerMark(Mark mark) throws Exception;

	Execution<Object> getGradeDeparterMap(String name) throws Exception;

	Execution<Integer> submitMark(Mark mark) throws Exception;

	Execution<Object> getMarkDeparterMap(String name) throws Exception;
}
