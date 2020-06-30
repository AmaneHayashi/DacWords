package com.amane.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.amane.dto.Execution;
import com.amane.entity.Grade;
import com.amane.entity.Question;
/*
 * 业务接口：站在"使用者"角度设计接口 三个方面：方法定义粒度，参数，返回类型（return 类型/异常）
 */
public interface QuestionService {

	//Execution<Integer> register(Question question) throws Exception;

	Question getQuestionById(String id);

	//Execution<Integer> batchUpdateQid(List<Question> questionList) throws Exception;

	List<Question> getQuestionsById(String id);

	Execution<Integer> submitTest(String uid, Map<String, String> params) throws Exception;

	List<Map<String, String>> getQuestionJudgeById(String uid, String id);

	Execution<Integer> submitJudge(List<Grade> gradeList) throws Exception;

	Execution<String> modifyPic(String basePath, MultipartFile pic);

	Execution<Map<String, Integer>> getQs(String oid, String type) throws Exception;

	Execution<Integer> batchInsert(List<Question> qList) throws Exception;
	
}
