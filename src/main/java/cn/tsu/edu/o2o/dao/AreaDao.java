package cn.tsu.edu.o2o.dao;

import java.util.List;

import cn.tsu.edu.o2o.entity.Area;

/**
 * 
 * @author 宋维飞
 *
 */
public interface AreaDao {
	/**
	 * 列出区域列表
	 * @return areaList
	 */
	 List<Area> queryArea();

}
