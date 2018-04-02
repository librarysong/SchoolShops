package cn.tsu.edu.o2o.service;

import java.util.List;

import cn.tsu.edu.o2o.dto.ImageHolder;
import cn.tsu.edu.o2o.dto.ProductExecution;
import cn.tsu.edu.o2o.entity.Product;
import cn.tsu.edu.o2o.exceptions.ProductOperationException;

public interface ProductService {
	
	
/*	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	Product getProductById(long productId);*/

	ProductExecution addProduct(Product product, ImageHolder thumbnail,List<ImageHolder> thumbnailList)
			throws ProductOperationException;


}
