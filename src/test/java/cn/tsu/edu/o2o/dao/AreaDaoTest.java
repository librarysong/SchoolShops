package cn.tsu.edu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.tsu.edu.o2o.BaseTest;
import cn.tsu.edu.o2o.entity.Area;

/**
 * 
 * @author 宋维飞
 *
 */

public class AreaDaoTest extends BaseTest {
	
	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void testQueryArea() throws Exception {
		List<Area> areaList = areaDao.queryArea();
		assertEquals(2, areaList.size());
	}
	
	

}
