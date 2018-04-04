package cn.tsu.edu.o2o.service;

import java.util.List;

import cn.tsu.edu.o2o.dto.ImageHolder;
import cn.tsu.edu.o2o.dto.ProductExecution;
import cn.tsu.edu.o2o.entity.Product;
import cn.tsu.edu.o2o.exceptions.ProductOperationException;

public interface ProductService {
	
	/**
	 * 查询商品列表并分页，可输入的条件有:商品名(模糊) 商品状态  店铺id   商品类别
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
   /**
    * 通过商品ID查询唯一的商品信息
    * @param productId
    * @return
    */
	Product getProductById(long productId);
	
	/**
	 * 添加商品信息以及图片
	 * @param product
	 * @param thumbnail
	 * @param thumbnailList
	 * @return
	 * @throws ProductOperationException
	 */

	ProductExecution addProduct(Product product, ImageHolder thumbnail,List<ImageHolder> thumbnailList)
			throws ProductOperationException;

	/**
	 * 修改商品信息
	 * @param product
	 * @param thumbnail
	 * @param thumbnailList
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail,List<ImageHolder> thumbnailList)
	        throws ProductOperationException;

}
