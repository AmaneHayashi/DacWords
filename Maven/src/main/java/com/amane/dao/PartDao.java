package com.amane.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.amane.entity.Collect;
import com.amane.entity.Nav;
import com.amane.entity.QTNav;
import com.amane.entity.User;

public interface PartDao {
	/*
	 * 通过ID查询单个用户
	 * 
	 * @param uid
	 * @return
	 */
	
	List<Map<String, Object>> queryAll(@Param("uid") String uid, @Param("type") String type, @Param("kind") String kind, 
			@Param("offset") int offset, @Param("limit") int limit);
	
	List<Nav> queryNav();

	String getType(String listType);

	int getItems(@Param("tableName") String tableName, @Param("kind") String kind);
	
	List<Map<String, String>> queryAbbr();
	
	List<QTNav> queryQTNav(String kind);

	Map<String, String> queryFormatter(String kind);
	
	int collectIncrease(@Param("collect") Collect c);
	
	int collectDecrease(@Param("collect") Collect c);

	Map<String, Object> queryDetail(@Param("type") String type, @Param("id") String id);
	
	List<Map<String, Object>> queryCreations(String name);

	List<Map<String, Object>> queryCollections(String name);

	Map<String, Object> queryBlockquotes(int offset);
	
	List<Map<String, Object>> queryHotspot();
	
	List<Map<String, Object>> queryAlert(@Param("user") User user);

	List<Map<String, String>> queryErrors();
}
