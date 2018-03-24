package cn.tsu.edu.o2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.tsu.edu.o2o.dao.ShopDao;
import cn.tsu.edu.o2o.dto.ShopExecution;
import cn.tsu.edu.o2o.entity.Shop;
import cn.tsu.edu.o2o.enums.ShopStateEnum;
import cn.tsu.edu.o2o.exceptions.ShopOperationException;
import cn.tsu.edu.o2o.service.ShopService;
import cn.tsu.edu.o2o.util.ImageUtil;
import cn.tsu.edu.o2o.util.PathUtil;

@Service
public class ShopServiceImpl  implements ShopService{
	
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) {
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		}
		
		try {
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			int effectNum=shopDao.insertShop(shop);
			if(effectNum<=0) {
				throw new ShopOperationException("店铺创建失败");
			}else {
				
				if(shopImgInputStream!=null) {
					try {
						//存储图片
						addShopImg(shop, shopImgInputStream,fileName);
					}catch(Exception e) {
						throw new ShopOperationException("addShopImg error："+e.getMessage());
					}
					
					//更新店铺图片地址
					effectNum=shopDao.updateShop(shop);
					if(effectNum<=0) {
						throw new ShopOperationException("更新图片地址失败");
					}
					
				}
			}
		}catch(Exception e) {
			throw new ShopOperationException("addShop error："+e.getMessage());
		}
		
		return new ShopExecution(ShopStateEnum.CHECK,shop);
		
	}
	
	private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) {
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName, dest);
		shop.setShopImg(shopImgAddr);
		
	}

}
