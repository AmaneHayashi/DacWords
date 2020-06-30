package com.amane.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amane.dao.PartDao;
import com.amane.dto.Execution;
import com.amane.entity.Collect;
import com.amane.entity.Nav;
import com.amane.entity.QTNav;
import com.amane.entity.User;
import com.amane.enums.NormalStateEnum;
import com.amane.exception.EmptyFieldException;
import com.amane.exception.SystemErrorException;
import com.amane.service.PartService;
import com.amane.utils.BeanTools;

import lombok.Data;

@Service
@Data
public class PartServiceImpl implements PartService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static Map<String, String> FULLNAME_MAP = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("T", "Test");
			put("Q", "Question");
			put("A", "Article");
		}
	};
	
	@SuppressWarnings("serial")
	public final static Map<String, Integer> LIST_LIMIT_MAP = new HashMap<String, Integer>(){
		{
			put("test", 5 + 1);
			put("question", 8 + 1);
			put("article", 4 + 1);
		}
	};
	
	// 注入Service依赖
	@Autowired
	private PartDao partDao;
	
	@Transactional
	public Execution<Map<Integer, String>> getNavMap() throws Exception {
		try {
			List<Nav> navList = partDao.queryNav();
			Map<Integer, String> navMap = new LinkedHashMap<Integer, String>();
			Iterator<Nav> navListIterator = navList.iterator();
			int g = 1;
			while(navListIterator.hasNext()) {
				Nav nav = navListIterator.next();
				int pp = nav.getPprank() * 100;
				int p = nav.getPrank() * 10;
				if(navMap.get(pp + p) == null) {
					if(navMap.get(pp) == null) {
						navMap.put(pp, nav.getPpname());
					}
					navMap.put(pp + p, nav.getPname());
					g = 1;
				}
				navMap.put(pp + p + g, nav.getName());
				g++;
			}
			return new Execution<Map<Integer, String>>(NormalStateEnum.SUCCESS, navMap);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("navmap inner error:" + e.getMessage());	
		}
	}

	@Override
	public Execution<String> getType(String listType) throws Exception {
		try {
			String type = partDao.getType(listType);
			type = FULLNAME_MAP.get(type);
			if(type == null) {
				throw new EmptyFieldException("empty stdout");
			}
			else {
				return new Execution<String>(NormalStateEnum.SUCCESS, type);
			}
		} catch(EmptyFieldException e1) {
			throw e1;
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("getType inner error:" + e.getMessage());
		}
	}

	@Override
	public Execution<Integer> getItems(String tableName, String kind) throws Exception {
		try {
			int items = partDao.getItems(tableName, kind);
			return new Execution<Integer>(NormalStateEnum.SUCCESS, items);
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("getType inner error:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> getAbbr() {
		List<Map<String, String>> mapList = partDao.queryAbbr();
		Map<String, String> abbrMap = new HashMap<String, String>();
		mapList.forEach(map -> {
			abbrMap.put(map.get("name"), map.get("abbr"));
		});
		return abbrMap;
	}

	@Override
	public Map<Integer, String> getQTNavMap(String kind) throws Exception {
		List<QTNav> qtNavList = partDao.queryQTNav(kind);
		Iterator<QTNav> qtNavIterator = qtNavList.iterator();
		Map<Integer, String> qtNavMap = new LinkedHashMap<Integer, String>();
		while(qtNavIterator.hasNext()) {
			QTNav qtn = qtNavIterator.next();
			int pp = qtn.getPprank() * 100;
			int p = qtn.getPrank() * 10;
			int g = qtn.getCrank();
			if(qtNavMap.get(pp + p) == null) {
				if(qtNavMap.get(pp) == null) {
					qtNavMap.put(pp, qtn.getField());
				}
				qtNavMap.put(pp + p, qtn.getSubject());
			}
			qtNavMap.put(pp + p + g, qtn.getPoint());
		}
		return qtNavMap;
	}

	@Override
	public Map<String, String> getFormatter(String kind) {
		return partDao.queryFormatter(kind);
	}
	
	@Override
	public List<JSONObject> getList(String uid, String type, String kind, int page) throws Exception {
		try {
			int limit = LIST_LIMIT_MAP.get(type);
			List<Map<String, Object>> queryList = partDao.queryAll(uid, type, kind, (page - 1) * (limit - 1), limit);
			if(queryList.size() == 0) {
				throw new EmptyFieldException("no such element");
			}
			else {
				return queryList
						.parallelStream()
						.map(JSONObject::new)
						.collect(Collectors.toList());
			}
		}catch(EmptyFieldException e1) {
			throw e1;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("login inner error:" + e.getMessage());
		}
	}

	@Override
	public Execution<Integer> collect(String type, Collect c) throws Exception {
		try{
			int update = Integer.parseInt(partDao.getClass().getDeclaredMethod("collect" + type, Collect.class).invoke(partDao, c).toString());
			return new Execution<Integer>(NormalStateEnum.SUCCESS, update);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("login inner error:" + e.getMessage());
		}
	}

	@Override
	public Map<String, Object> queryDetail(String type, String id) throws Exception {
		return partDao.queryDetail(type, id);
	}

	@Override
	public Execution<Object> getCreations(String name) throws Exception {
		try {
			List<Map<String, Object>> mapList = partDao.queryCreations(name);
			return new Execution<Object>(NormalStateEnum.SUCCESS, BeanTools.datify(mapList));
		} catch(IndexOutOfBoundsException e1) {
			throw new EmptyFieldException("no results");
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());				
		}
	}

	@Override
	public Execution<Object> getCollections(String name) throws Exception {
		try {
			List<Map<String, Object>> mapList = partDao.queryCollections(name);
			return new Execution<Object>(NormalStateEnum.SUCCESS, BeanTools.datify(mapList));
		} catch(IndexOutOfBoundsException e1) {
			throw new EmptyFieldException("no results");
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());				
		}
	}

	@Override
	public Execution<Object> getBlockQuotes() throws Exception {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int offset = cal.get(Calendar.DAY_OF_WEEK) - 1;
			Map<String, Object> blockquoteMap = partDao.queryBlockquotes(offset);
			return new Execution<Object>(NormalStateEnum.SUCCESS, new JSONObject(blockquoteMap));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());				
		}
	}

	@Override
	public Execution<Object> getHotspot() throws Exception {
		try {
			List<Map<String, Object>> hotspotMap = partDao.queryHotspot();
			return new Execution<Object>(NormalStateEnum.SUCCESS, JSON.toJSON(BeanTools.datify(hotspotMap)));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());				
		}
	}

	@Override
	public Execution<Object> getAlerts(User user) throws Exception {
		try {
			List<Map<String, Object>> alertMap = partDao.queryAlert(user);
			return new Execution<Object>(NormalStateEnum.SUCCESS, JSON.toJSON(BeanTools.datify(alertMap)));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemErrorException("register inner error:" + e.getMessage());				
		}
	}

	@Override
	public List<Map<String, String>> getErrors() {
		return partDao.queryErrors();
	}
}
