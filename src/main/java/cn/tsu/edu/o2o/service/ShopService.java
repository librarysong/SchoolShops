package cn.tsu.edu.o2o.service;

import cn.tsu.edu.o2o.dto.ImageHolder;
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
	 * 根据shopcodition分页查询数据
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
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
	 ShopExecution modifyShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;
	
	
     ShopExecution addShop(Shop shop,ImageHolder thumbnail);
}
