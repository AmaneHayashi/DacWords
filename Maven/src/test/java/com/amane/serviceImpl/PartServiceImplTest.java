package com.amane.serviceImpl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amane.BaseTest;
import com.amane.service.impl.PartServiceImpl;

@Service
public class PartServiceImplTest extends BaseTest {

	@Autowired
	private PartServiceImpl partServiceImpl;
/*
	@Test
	public void testGetNavMap() throws Exception {
		Execution<Map<Integer, String>> navMap = partServiceImpl.getNavMap();
		System.out.println(navMap.getData());
	}*/
	/*{100=「文·手记」, 110=〔书肆〕, 111=方圆之道, 112=落英缤纷, 120=〔触言〕, 121=天音, 122=茶歇, 
	 * 200=「题·夏试」, 210=〔考试〕, 211=夏试, 212=考研, 220=〔习题〕, 221=集萃}**/
	
	@Test
	public void testGetAbbr() throws Exception {
	//	partServiceImpl.getList("test", "考研", 0);
	}
}
