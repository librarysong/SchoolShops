package cn.tsu.edu.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tsu.edu.o2o.entity.HeadLine;

public interface HeadLineDao {

	/**
	 * 
	 * @return
	 */
	List<HeadLine> queryHeadLine(
			@Param("headLineCondition") HeadLine headLineCondition);

}
