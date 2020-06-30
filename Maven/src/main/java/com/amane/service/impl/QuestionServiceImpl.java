package com.amane.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amane.dao.QuestionDao;
import com.amane.dto.Execution;
import com.amane.entity.Grade;
import com.amane.entity.Question;
import com.amane.enums.NormalStateEnum;
import com.amane.exception.DuplicateException;
import com.amane.exception.SystemErrorException;
import com.amane.service.QuestionService;
import lombok.Data;

@Service
@Data
public class QuestionServiceImpl implements QuestionService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 注入Service依赖
	@Autowired
	private QuestionDao questionDao;

	private final static String FILE_PATH = "resources/images/uploads/";

	/*
	 * 使用注解控制事务方法的优点： 1.开发团队达成一致约定，明确标注事务方法的编程风格
	 * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外部
	 * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
	 */

	@Transactional
	@Override
	public Question getQuestionById(String id) {
		return questionDao.queryById(id);
	}
/*
	@Override
	public Execution<Integer> batchUpdateQid(List<Question> questionList) throws Exception {
		try {
			// 在c_super完成注册
			int register = questionDao.batchUpdateQid(questionList);
			if (register < questionList.size()) {
				throw new DuplicateException("repeat id");
			} else {
				return new Execution<Integer>(NormalStateEnum.SUCCESS, register);
			}
		} catch (DuplicateException e1) {
			throw e1;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());
		}
	}
*/
	@Override
	public List<Question> getQuestionsById(String id) {
		// TODO Auto-generated method stub
		return questionDao.queryByTid(id);
	}

	@Override
	public Execution<Integer> submitTest(String uid, Map<String, String> params) throws Exception {
		try {
			Date date = new Date();
			List<Grade> gradeList = new ArrayList<Grade>();
			params.keySet().forEach(k -> {
				Grade grade = new Grade();
				grade.setUid(uid);
				grade.setQid(k);
				grade.setAnswer(params.get(k));
				grade.setAnswerTime(date);
				gradeList.add(grade);
			});
			int records = questionDao.batchRegisterGrade(gradeList);
			if (records != gradeList.size()) {
				throw new DuplicateException("repeat q/uid");
			} else {
				return new Execution<Integer>(NormalStateEnum.SUCCESS, records);
			}
		} catch (DuplicateException e1) {
			throw e1;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("submitTest inner error:" + e.getMessage());
		}
	}

	@Override
	public List<Map<String, String>> getQuestionJudgeById(String uid, String id) {
		return questionDao.queryJudgeByTid(uid, id);
	}

	@Override
	public Execution<Integer> submitJudge(List<Grade> gradeList) throws Exception {
		try {
			int update = questionDao.batchUpdateGrade(gradeList);
			if (update != gradeList.size()) {
				throw new DuplicateException("duplicated");
			}
			return new Execution<Integer>(NormalStateEnum.SUCCESS, update);
		} catch (DuplicateException e1) {
			throw e1;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());
		}
	}

	//压缩,格式统一(.jpg .png)
	@Override
	public Execution<String> modifyPic(String basePath, MultipartFile file) {
		try {
			if (!file.isEmpty()) {
				//确定完整路径
				String absolutePath = basePath + FILE_PATH;
				//寻找文件夹
				File dir = new File(absolutePath);
				if(!dir.isDirectory()) {
					dir.mkdir();
				}
				//确定文件名
				String originName = file.getOriginalFilename();
				String fileName = String.valueOf(new Date().getTime() + originName.substring(originName.indexOf(".")));
				//新建文件
				File newFile = new File(absolutePath + fileName);
				System.out.println(newFile.getAbsolutePath());
				//文件转移
				file.transferTo(newFile);
				return new Execution<String>(NormalStateEnum.SUCCESS, fileName);
			} else {
				return new Execution<String>(NormalStateEnum.SUCCESS, "");
			} 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new Execution<String>(NormalStateEnum.SUCCESS, "");
		}
	}

	@Override
	public Execution<Map<String, Integer>> getQs(String oid, String KIND) throws Exception {
		try {
			Map<String, Integer> qInfoMap = questionDao.queryQInfo(oid, KIND);
			return new Execution<Map<String, Integer>>(NormalStateEnum.SUCCESS, qInfoMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("submitTest inner error:" + e.getMessage());
		}
	}

	@Override
	public Execution<Integer> batchInsert(List<Question> qList) throws Exception {
		try {
			// 在c_super完成注册
			int register = questionDao.batchInsert(qList);
			if (register < qList.size()) {
				throw new DuplicateException("repeat id");
			} else {
				return new Execution<Integer>(NormalStateEnum.SUCCESS, register);
			}
		} catch (DuplicateException e1) {
			throw e1;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());
		}
	}
}