package cn.tsu.edu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.tsu.edu.o2o.BaseTest;
import cn.tsu.edu.o2o.entity.Area;
import cn.tsu.edu.o2o.entity.PersonInfo;
import cn.tsu.edu.o2o.entity.Shop;
import cn.tsu.edu.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest {

	@Autowired
	private ShopDao shopDao;
	
	@Test
	public void testQueryByShopId() {
		long shopId=1;
		Shop shop=shopDao.queryByShopId(shopId);
		System.out.println(shop.getArea().getAreaId());
		System.out.println(shop.getArea().getAreaName());
	}
	
	@Test
	@Ignore
	public void testInsertShop()
	{
		Shop shop=new Shop();
		PersonInfo owner=new PersonInfo();
		Area area=new Area();
		ShopCategory shopCategory=new ShopCategory();
		
		owner.setUserId(1L);
		area.setAreaId(1L);
		shopCategory.setShopCategoryId(1L);
		
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		
		int effectedNum=shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
		
	}
	
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop=new Shop();
		shop.setShopId(1L);
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}
}
