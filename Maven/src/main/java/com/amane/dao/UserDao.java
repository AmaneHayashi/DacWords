package com.amane.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.amane.entity.User;

public interface UserDao {
	/*
	 * 通过ID查询单个用户
	 * 
	 * @param uid
	 * @return
	 */
	User queryById(String uid);

	/*
	 * 通过Name查询单个用户
	 * 
	 * @param name
	 * @return
	 */
	User queryByName(String name);
	
	/*
	 * 获得用户数
	 * 
	 * @return int
	 */
	int getUsers();
	
	/*
	 * 注册一个新用户(主表)
	 * 
	 * @param user
	 * @return 如果影响行数等于>1，表示更新的记录行数
	 */
	int register(@Param("user") User user);
	
	/*
	 * 注册一个新用户(子表)
	 * 
	 * @param user
	 * @return 如果影响行数等于>1，表示更新的记录行数
	 */
	int registerSub(@Param("user") User user);
	
	/*
	 * 更新用户数据
	 * 
	 * @param user
	 * @return 如果影响行数等于>1，表示更新的记录行数
	 */
	//int update(@Param("user") User user);

	User test();

	int updateLogin(@Param("user")User user);

	Map<String, Integer> getParams(String uid);
	
}
