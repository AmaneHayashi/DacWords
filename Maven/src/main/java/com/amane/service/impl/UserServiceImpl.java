package com.amane.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amane.dao.UserDao;
import com.amane.dto.Execution;
import com.amane.entity.User;
import com.amane.enums.NormalStateEnum;
import com.amane.exception.DuplicateException;
import com.amane.exception.SystemErrorException;
import com.amane.exception.WrongInputException;
import com.amane.service.UserService;
import com.amane.utils.MD5Tools;

import lombok.Data;

@Service
@Data
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 注入Service依赖
	@Autowired
	private UserDao userDao;

	public User queryUserById(String uid) {
		return userDao.queryById(uid);
	}
	/*
	 * 使用注解控制事务方法的优点： 1.开发团队达成一致约定，明确标注事务方法的编程风格
	 * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外部
	 * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
	 */
	
	@Transactional
	public Execution<User> register(User user) throws Exception {
		try {
			//设置用户信息
		//	Date date = new Date();
		//	user.setLastlogin(date);
		//	user.setRegistime(date);
			user.setPswd(MD5Tools.md5(user.getPswd()));
			logger.info(user.toString());
			//开始注册
			int register = userDao.register(user) + userDao.registerSub(user);
			if(register <= 0) {
				throw new DuplicateException("repeat username");
			}
			else {
				//安全措施
				user.setPswd("");
				return new Execution<User>(NormalStateEnum.SUCCESS, user);
			}
		}catch(DuplicateException e1) {
			throw e1;
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());
		}
	}
	
	@Transactional
	public Execution<User> login(User user) throws Exception {
		try {
			//开始登录
			user.setPswd(MD5Tools.md5(user.getPswd()));
			int update = userDao.updateLogin(user);
			if(update < 1) {
				throw new WrongInputException("wrong input");
			}
			else {
				User auser = userDao.queryByName(user.getName());
				//安全措施
				auser.setPswd("");
				return new Execution<User>(NormalStateEnum.SUCCESS, auser);
			}
		}catch(WrongInputException e1) {
			throw e1;
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("login inner error:" + e.getMessage());
		}
	}

	@Transactional
	public Execution<String> getUid() throws Exception {
		try {
			int users = userDao.getUsers();
			return new Execution<String>(NormalStateEnum.SUCCESS, String.format("202001%s", String.format("%03d", users)));
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("getuid inner error:" + e.getMessage());			
		}
	}

	@Transactional
	public Execution<User> queryUserByName(String name) throws Exception {
		try{
			User user = userDao.queryByName(name);
			if(user != null) {
				throw new DuplicateException("repeat object");
			}
			else {
				return new Execution<User>(NormalStateEnum.SUCCESS, user);
			}
		} catch(DuplicateException e1) {
			throw e1;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("queryname inner error:" + e.getMessage());				
		}
	}

	@Transactional
	public void test() throws Exception {
		try {
			userDao.test();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("queryname inner error:" + e.getMessage());				
		}
	}
	@Override
	public Map<String, Integer> getParams(String uid) {
		return userDao.getParams(uid);
	}
}