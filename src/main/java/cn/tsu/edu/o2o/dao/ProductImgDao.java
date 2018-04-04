package cn.tsu.edu.o2o.dao;

import java.util.List;

import cn.tsu.edu.o2o.entity.ProductImg;

public interface ProductImgDao {
	
	List<ProductImg> queryProductImgList(long productId);

	/**
	 * 批量添加商品详情图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	
	
	/**
	 * 删除指定商品下的所有详情图片
	 * @param productId
	 * @return
	 */

	int deleteProductImgByProductId(long productId);

}
