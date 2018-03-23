package cn.tsu.edu.o2o.service;

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
public class AreaServiceTest  extends BaseTest{

	@Autowired
	private AreaService areaService;
	
	@Test
	public void testGetAreaList() {
		List<Area> list = areaService.getAreaList();
		assertEquals("西苑", list.get(0).getAreaName());
	}
}
