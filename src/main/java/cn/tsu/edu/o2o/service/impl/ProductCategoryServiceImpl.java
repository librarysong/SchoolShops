package cn.tsu.edu.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tsu.edu.o2o.dao.ProductCategoryDao;
import cn.tsu.edu.o2o.dto.ProductCategoryExecution;
import cn.tsu.edu.o2o.entity.ProductCategory;
import cn.tsu.edu.o2o.enums.ProductCategoryStateEnum;
import cn.tsu.edu.o2o.exceptions.ProductCategoryOperationException;
import cn.tsu.edu.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {
		// TODO Auto-generated method stub
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		if(productCategoryList!=null && productCategoryList.size()>0) {
			try {
				
				int effectedNum=productCategoryDao.batchInsertProductCategory(productCategoryList);
				if(effectedNum<=0) {
					throw new ProductCategoryOperationException("店铺类别创建失败");
				}else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			}catch(Exception e) {
				throw new ProductCategoryOperationException("batchaddproductcategory error："+e.getMessage());
			}
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		//TODO  将此商品类别下的商品的类别id置为空
		try {
			int effectNum=productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if(effectNum<=0) {
				throw new ProductCategoryOperationException("店铺商品类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
			
		}catch(Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error："+e.getMessage());
		}
	}
}
