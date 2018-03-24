package cn.tsu.edu.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.tsu.edu.o2o.BaseTest;
import cn.tsu.edu.o2o.dto.ShopExecution;
import cn.tsu.edu.o2o.entity.Area;
import cn.tsu.edu.o2o.entity.PersonInfo;
import cn.tsu.edu.o2o.entity.Shop;
import cn.tsu.edu.o2o.entity.ShopCategory;
import cn.tsu.edu.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;
	
	@Test
	public  void testAddShop() throws FileNotFoundException
	{
		Shop shop = new Shop();
	    PersonInfo owner=new PersonInfo();
	    Area area=new Area();
	    ShopCategory shopCategory=new ShopCategory();
	    owner.setUserId(1L);
	    area.setAreaId(2L);
	    shopCategory.setShopCategoryId(1L);
	    shop.setOwner(owner);
	    shop.setArea(area);
	    shop.setShopCategory(shopCategory);
	    shop.setShopName("测试的店铺3");
	    shop.setShopDesc("test3");
	    shop.setShopAddr("test3");
	    shop.setPhone("test3");
	    shop.setCreateTime(new Date());
	    shop.setEnableStatus(ShopStateEnum.CHECK.getState());
	    shop.setAdvice("审核中");
	    File shopImg=new File("E:/image/xiaohuangren.jpg");
	    InputStream is=new FileInputStream(shopImg);
	    ShopExecution se=shopService.addShop(shop, is,shopImg.getName());
	    assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
	}

}
