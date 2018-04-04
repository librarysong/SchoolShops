package cn.tsu.edu.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tsu.edu.o2o.dao.ProductDao;
import cn.tsu.edu.o2o.dao.ProductImgDao;
import cn.tsu.edu.o2o.dto.ImageHolder;
import cn.tsu.edu.o2o.dto.ProductExecution;
import cn.tsu.edu.o2o.entity.Product;
import cn.tsu.edu.o2o.entity.ProductImg;
import cn.tsu.edu.o2o.enums.ProductStateEnum;
import cn.tsu.edu.o2o.exceptions.ProductOperationException;
import cn.tsu.edu.o2o.service.ProductService;
import cn.tsu.edu.o2o.util.ImageUtil;
import cn.tsu.edu.o2o.util.PageCalculator;
import cn.tsu.edu.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductImgDao productImgDao;

	@Override
	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> ProductImgHolderList)
			throws ProductOperationException {

		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			//设置默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			product.setEnableStatus(1);
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new RuntimeException("创建商品失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品失败:" + e.toString());
			}
			//若商品详情图不为空，则添加
			if (ProductImgHolderList != null && ProductImgHolderList.size() > 0) {
				addProductImgs(product, ProductImgHolderList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
	
	/**
	 * 添加缩略图
	 * @param product
	 * @param thumbnail
	 */
	private void addThumbnail(Product product,ImageHolder thumbnail) {
		String dest=PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr=ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}
	/**
	 * 批量添加图片
	 * @param product
	 * @param productImgHolders
	 */
	private void addProductImgs(Product product,List<ImageHolder> productImgHolderList) {
		String dest=PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList=new ArrayList<ProductImg>();
		//遍历图片集合去处理
		
		for(ImageHolder productImgHolder:productImgHolderList) {
			String imgAddr=ImageUtil.generateThumbnail(productImgHolder, dest);
			ProductImg productImg=new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
			
		}
		
		//如果确实是有图片需要添加，就执行此操作
		if(productImgList.size()>0) {
			try {
				
				int effectNum=productImgDao.batchInsertProductImg(productImgList);
				if(effectNum<=0) {
					throw new ProductOperationException("创建商品详情图片失败");
				}
			}catch(Exception e) {
				throw new ProductOperationException("创建商品详情图片失败："+e.toString());
			}
		}
	}

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}

	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHodlerList)
			throws ProductOperationException {
		//空值判断
		if(product!=null && product.getShop()!=null && product.getShop().getShopId()!=null) {
			//给商品设置上默认属性
			product.setLastEditTime(new Date());
			
			//若商品缩略图不为空且原有缩略图不为空则删除原来的
			if(thumbnail!=null) {
				Product temProduct=productDao.queryProductById(product.getProductId());
				if(temProduct.getImgAddr()!=null) {
					ImageUtil.deleteFileOrPath(temProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}
			
			//如果有新存入的商品详情图，则将原来的先删除，再添加新的
			if(productImgHodlerList!=null && productImgHodlerList.size()>0) {
				//
				deleteProductImgList(product.getProductId());
				addProductImgs(product, productImgHodlerList);
				
			}
			try {
				
				//更新商品信息
				int effectNum=productDao.updateProduct(product);
				if(effectNum<=0) {
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS,product);
			}catch(Exception e) {
				throw new ProductOperationException("更新商品信息失败:"+e.toString());
			}
			
		}else {
			
			return new ProductExecution(ProductStateEnum.EMPTY);
		}

	}
	
	
	private void deleteProductImgList(long productId) {
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		productImgDao.deleteProductImgByProductId(productId);
	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}
	
	

}
