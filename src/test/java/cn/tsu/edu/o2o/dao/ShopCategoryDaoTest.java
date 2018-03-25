package cn.tsu.edu.o2o.dao;

import static org.junit.Assert.assertEquals;

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
		List<ShopCategory> shopCategories=shopCategoryDao.queryShopCategory(new ShopCategory());
		assertEquals(2,shopCategories.size());
		
		ShopCategory testCategory=new ShopCategory();
		ShopCategory parentCategory=new ShopCategory();
		parentCategory.setShopCategoryId(1L);
		testCategory.setParent(parentCategory);
		shopCategories=shopCategoryDao.queryShopCategory(testCategory);
		assertEquals(1, shopCategories.size());
		System.out.println(shopCategories.get(0).getShopCategoryName());
	}

}
