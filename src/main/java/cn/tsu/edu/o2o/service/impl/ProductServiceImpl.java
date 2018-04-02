package cn.tsu.edu.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tsu.edu.o2o.dao.ProductDao;
import cn.tsu.edu.o2o.dao.ProductImgDao;
import cn.tsu.edu.o2o.dto.ImageHolder;
import cn.tsu.edu.o2o.dto.ProductExecution;
import cn.tsu.edu.o2o.entity.Product;
import cn.tsu.edu.o2o.exceptions.ProductOperationException;
import cn.tsu.edu.o2o.service.ProductService;

@Service
public class ProductServiceImpl  implements ProductService{

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductImgDao productImgDao;
	
	@Override
	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> thumbnailList)
			throws ProductOperationException {
		
		return null;
	}

}
