package com.amane.service;

import java.util.Map;

import com.amane.dto.Execution;
import com.amane.entity.User;

/*
 * 业务接口：站在"使用者"角度设计接口 三个方面：方法定义粒度，参数，返回类型（return 类型/异常）
 */
public interface UserService {
	
	/*
	 * 查询用户
	 * 
	 * @param uId
	 * @return User
	 */
	User queryUserById(String uid);
	
	/*
	 * 查询用户
	 * 
	 * @param uId
	 * @return User
	 * @throws Exception 
	 */
	Execution<User> queryUserByName(String name) throws Exception;
	
	/*
	 * 获得用户数
	 * 
	 * @return int
	 * @throws Exception 
	 */
	Execution<String> getUid() throws Exception;
	
	/*
	 * 注册
	 * 
	 * @param bookId
	 * @return RegisterExecution
	 * @throws Throwable 
	 */
	Execution<User> register(User user) throws Exception;
	
	/*
	 * 登录
	 * 
	 * @param bookId
	 * @return RegisterExecution
	 * @throws Throwable 
	 */
	Execution<User> login(User user) throws Exception;

	/*
	 * 数据库连接测试
	 * @throws Exception 
	 * 
	 */
	void test() throws Exception;

	Map<String, Integer> getParams(String uid);
}
