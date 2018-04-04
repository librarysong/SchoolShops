package cn.tsu.edu.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.tsu.edu.o2o.BaseTest;
import cn.tsu.edu.o2o.dto.ImageHolder;
import cn.tsu.edu.o2o.dto.ProductExecution;
import cn.tsu.edu.o2o.entity.Product;
import cn.tsu.edu.o2o.entity.ProductCategory;
import cn.tsu.edu.o2o.entity.Shop;
import cn.tsu.edu.o2o.enums.ProductStateEnum;
import cn.tsu.edu.o2o.exceptions.ShopOperationException;


public class ProductServiceTest extends BaseTest {

	@Autowired
	private ProductService productService;
	
	@Test
	@Ignore
	public void testAddProduct() throws ShopOperationException,FileNotFoundException{
		
		Product product=new Product();
		Shop shop=new Shop();
		shop.setShopId(1L);
		ProductCategory pc=new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		
		//创建缩略图文件流
		File thumanilFile=new File("E://image/xiaohuangren.jpg");
		InputStream is=new FileInputStream(thumanilFile);
		ImageHolder thumbnail=new ImageHolder(thumanilFile.getName(), is);
		//创建两个商品详情图文件流
		File productImg1=new File("E://image/xiaohuangren.jpg");
		InputStream s1=new FileInputStream(productImg1);
		File productImg2=new File("E://image/dabai.jpg");
		InputStream s2=new FileInputStream(productImg2);
		
		List<ImageHolder> productImgList=new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(), s1));
		productImgList.add(new ImageHolder(productImg2.getName(), s2));
		
		//添加商品并验证
		ProductExecution pe=productService.addProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}
	
	@Test
	public void testModifyProduct() throws ShopOperationException,FileNotFoundException{
		
		Product product=new Product();
		Shop shop=new Shop();
		shop.setShopId(1L);
		
		ProductCategory pc=new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setProductId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("正式的商品");
		product.setProductDesc("正式的商品");
		
		//创建缩略图文件流
		File thuFile=new File("E://image/xiaohuangren.jpg");
		InputStream is=new FileInputStream(thuFile);
		
		ImageHolder thumbnail=new ImageHolder(thuFile.getName(), is);
		//创建两个商品详情图文件
		
		File productImg1=new File("E://image/dabai.jpg");
		InputStream is1=new FileInputStream(productImg1);
		File productImg2=new File("E://image/ercode.jpg");
		InputStream is2=new FileInputStream(productImg2);
		
		List<ImageHolder> productImgList=new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(), is1));
		productImgList.add(new ImageHolder(productImg2.getName(), is2));
		
		//添加商品并验证
		ProductExecution pe=productService.modifyProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
		
		
		
	}
	
	
	
}
