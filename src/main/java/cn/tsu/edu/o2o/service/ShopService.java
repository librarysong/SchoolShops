package cn.tsu.edu.o2o.service;

import java.io.InputStream;

import cn.tsu.edu.o2o.dto.ShopExecution;
import cn.tsu.edu.o2o.entity.Shop;
import cn.tsu.edu.o2o.exceptions.ShopOperationException;

/**
 * 
 * @author 宋维飞
 *
 */
public interface ShopService {
	
	/**
	 * 查询指定店铺信息
	 * 
	 * @param long
	 *            shopId
	 * @return Shop shop
	 */
	Shop getByShopId(long shopId);
	

	/**
	 * 更新店铺信息（从店家角度）
	 * 
	 * @param areaId
	 * @param shopAddr
	 * @param phone
	 * @param shopImg
	 * @param shopDesc
	 * @return
	 * @throws RuntimeException
	 */
	 ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream,String fileName) throws ShopOperationException;
	
	
     ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName);
}
