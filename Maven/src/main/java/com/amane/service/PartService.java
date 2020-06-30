package com.amane.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.amane.dto.Execution;
import com.amane.entity.Collect;
import com.amane.entity.User;

/*
 * 业务接口：站在"使用者"角度设计接口 三个方面：方法定义粒度，参数，返回类型（return 类型/异常）
 */

public interface PartService {

	/*
	 * 将nav树状结构生成为map并导出
	 *
	 * @return Map<Integer, String>
	 * @throws Exception
	 */
	Execution<Map<Integer, String>> getNavMap() throws Exception;

	Execution<String> getType(String listType) throws Exception;

	Execution<Integer> getItems(String tableName, String kind) throws Exception;

	Map<String, String> getAbbr();

	Map<Integer, String> getQTNavMap(String kind) throws Exception;

	Map<String, String> getFormatter(String kind);

	List<JSONObject> getList(String uid, String type, String kind, int page) throws Exception;

	Execution<Integer> collect(String type, Collect c) throws Exception;

	Map<String, Object> queryDetail(String type, String id) throws Exception;

	Execution<Object> getCreations(String name) throws Exception;

	Execution<Object> getCollections(String name) throws Exception;

	Execution<Object> getBlockQuotes() throws Exception;

	Execution<Object> getHotspot() throws Exception;

	Execution<Object> getAlerts(User user) throws Exception;

	List<Map<String, String>> getErrors();
}
