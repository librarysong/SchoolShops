package cn.tsu.edu.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tsu.edu.o2o.dao.AreaDao;
import cn.tsu.edu.o2o.entity.Area;
import cn.tsu.edu.o2o.service.AreaService;

/**
 * 
 * @author 宋维飞
 *
 */
@Service
public class AreaServiceImpl  implements AreaService {

	@Autowired
	private AreaDao areaDao;
	@Override
	public List<Area> getAreaList() {
		// TODO Auto-generated method stub
		List<Area> list = areaDao.queryArea();
		return list;
	}

}
