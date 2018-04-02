package cn.tsu.edu.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tsu.edu.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	
	/***
	 * 通过shopid查询店铺商品类别
	 * @param shopId
	 * @return
	 */
	
	List<ProductCategory> queryProductCategoryList(long shopId);
	
	/**
	 * 新增商品类别
	 * 
	 * @param ProductCategory
	 *            productCategory
	 * @return effectedNum
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);

	/**
	 * 删除商品类别（初版，即只支持删除尚且没有发布商品的商品类别）
	 * 
	 * @param productCategoryId
	 * @param shopId
	 * @return effectedNum
	 */
	int deleteProductCategory(
			@Param("productCategoryId") long productCategoryId,
			@Param("shopId") long shopId);

}
