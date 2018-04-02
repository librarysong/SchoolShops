package cn.tsu.edu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.javassist.bytecode.LineNumberAttribute.Pc;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import cn.tsu.edu.o2o.BaseTest;
import cn.tsu.edu.o2o.entity.ProductCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCatrgoryDaoTest  extends BaseTest {
	
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Test
	public void testBQueryByShopId() throws Exception {
		long shopId = 1;
		List<ProductCategory> productCategoryList = productCategoryDao
				.queryProductCategoryList(shopId);
		System.out.println("该店铺自定义类别数为："+productCategoryList.size());

	}
	
	@Test
	public void testAInsertProductCategory() throws Exception {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryName("商品类别1");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setShopId(1L);
		ProductCategory productCategory2 = new ProductCategory();
		productCategory2.setProductCategoryName("商品类别2");
		productCategory2.setPriority(2);
		productCategory2.setCreateTime(new Date());
		productCategory2.setShopId(1L);
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.add(productCategory);
		productCategoryList.add(productCategory2);
		int effectedNum = productCategoryDao
				.batchInsertProductCategory(productCategoryList);
		assertEquals(2, effectedNum);
	}
	
	@Test
	public void testCDeleteProductCategory() throws Exception{
		long shopId=1;
		List<ProductCategory> productCategoryList=productCategoryDao.queryProductCategoryList(shopId);
		for(ProductCategory sc:productCategoryList) {
			if("商品类别1".equals(sc.getProductCategoryName())||"商品类别2".equals(sc.getProductCategoryName())) {
		      
				int effectedNum=productCategoryDao.deleteProductCategory(sc.getProductCategoryId(),shopId);
				assertEquals(1, effectedNum);
			}
		}
	}

}
