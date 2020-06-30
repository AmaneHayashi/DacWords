package com.amane.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.amane.entity.Grade;
import com.amane.entity.Question;

public interface QuestionDao {

	Question queryById(String id);

	List<Question> queryByTid(@Param("id") String id);

	int batchRegisterGrade(List<Grade> gradeList);

	List<Map<String, String>> queryJudgeByTid(@Param("uid") String uid, @Param("id") String id);

	int batchUpdateGrade(List<Grade> gradeList);

	Map<String, Integer> queryQInfo(@Param("oid") String oid, @Param("kind") String kind);

	int batchInsert(@Param("list") List<Question> qList);
}
