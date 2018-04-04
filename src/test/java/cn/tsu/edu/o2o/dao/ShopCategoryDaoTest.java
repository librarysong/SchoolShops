package cn.tsu.edu.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.tsu.edu.o2o.BaseTest;
import cn.tsu.edu.o2o.entity.ShopCategory;

/**
 * 
 * @author 宋维飞
 *
 */
public class ShopCategoryDaoTest extends BaseTest {
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Test
	public void testQueryShopCategory() {
		List<ShopCategory> shopCategories=shopCategoryDao.queryShopCategory(null);
		System.out.println(shopCategories.size());
		
	}

}
