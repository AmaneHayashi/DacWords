package com.amane.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.amane.entity.Mark;
import com.amane.entity.Test;

public interface TestDao {
	
	/*
	 * 注册一个新考试
	 * 
	 * @param test
	 * @return 如果影响行数等于>1，表示更新的记录行数
	 */
	int create(Test test);
	
	/*
	 * 根据ID查询考试
	 * 
	 * @param id
	 * @return
	 */
	Test queryById(String id);
	
	/*
	 * 查询所有考试
	 * 
	 * @param kind 考试类型
	 * @param offset 查询起始位置
	 * @param limit 查询条数
	 * @return
	 */

	int register(@Param("test") Test test);

	//int superRegister(@Param("test") Test test);

	int update(@Param("test") Test test);

	//int updateRegister(@Param("test") Test test);

	int registerMark(@Param("mark") Mark mark);
	
	int updateMark(@Param("mark") Mark mark);
	
	List<Map<String, Object>> queryGradeDeparter(String name);

	List<Map<String, Object>> queryMarkDeparter(String name);
}
