package com.amane.service.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amane.dao.TestDao;
import com.amane.dto.Execution;
import com.amane.entity.Mark;
import com.amane.entity.Test;
import com.amane.enums.NormalStateEnum;
import com.amane.exception.DuplicateException;
import com.amane.exception.EmptyFieldException;
import com.amane.exception.SystemErrorException;
import com.amane.service.TestService;
import com.amane.utils.BeanTools;
import lombok.Data;

@Service
@Data
public class TestServiceImpl implements TestService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 注入Service依赖
	@Autowired
	private TestDao testDao;
	
	/*
	 * 使用注解控制事务方法的优点： 1.开发团队达成一致约定，明确标注事务方法的编程风格
	 * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外部
	 * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
	 */
	
	@Transactional
	@Override
	public Execution<Integer> register(Test test) throws Exception {
		try{
			//在c_super完成注册
			int update = testDao.register(test);
			return new Execution<Integer>(NormalStateEnum.SUCCESS, update);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());				
		}
	}

	@Override
	public Execution<Integer> registerMark(Mark mark) throws Exception {
		try {
			int insert = testDao.registerMark(mark);
			if(insert < 1) {
				throw new DuplicateException("repeat marker");
			}
			return new Execution<Integer>(NormalStateEnum.SUCCESS, insert);
		} catch(DuplicateException e1) {
			throw e1;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());				
		}
	}

	@Override
	public Execution<Integer> submitMark(Mark mark) throws Exception {
		try {
			int update = testDao.updateMark(mark);
			if(update < 1) {
				throw new DuplicateException("repeat marker");
			}
			return new Execution<Integer>(NormalStateEnum.SUCCESS, update);
		} catch(DuplicateException e1) {
			throw e1;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());				
		}
	}

	@Override
	public Execution<Object> getGradeDeparterMap(String name) throws Exception {
		try {
			List<Map<String, Object>> mapList = testDao.queryGradeDeparter(name);
			return new Execution<Object>(NormalStateEnum.SUCCESS, BeanTools.datify(mapList));
		} catch(IndexOutOfBoundsException e1) {
			throw new EmptyFieldException("no results");
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());				
		}
	}

	@Override
	public Execution<Object> getMarkDeparterMap(String name) throws Exception {
		try {
			List<Map<String, Object>> mapList = testDao.queryMarkDeparter(name);
			return new Execution<Object>(NormalStateEnum.SUCCESS, BeanTools.datify(mapList));
		} catch(IndexOutOfBoundsException e1) {
			throw new EmptyFieldException("no results");
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());				
		}
	}
}