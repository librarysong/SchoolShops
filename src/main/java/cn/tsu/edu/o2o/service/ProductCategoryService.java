package cn.tsu.edu.o2o.service;

import java.util.List;

import cn.tsu.edu.o2o.dto.ProductCategoryExecution;
import cn.tsu.edu.o2o.entity.ProductCategory;
import cn.tsu.edu.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {
	
	List<ProductCategory> getProductCategoryList(long shopId);
	
	/**
	 * 
	 * @param productCategory
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchAddProductCategory(
			List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;
	
	/**
	 * 
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId,
			long shopId) throws ProductCategoryOperationException;
	

}
