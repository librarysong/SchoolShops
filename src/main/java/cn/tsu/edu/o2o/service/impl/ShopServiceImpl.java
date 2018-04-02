package cn.tsu.edu.o2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.tsu.edu.o2o.dao.ShopDao;
import cn.tsu.edu.o2o.dto.ImageHolder;
import cn.tsu.edu.o2o.dto.ShopExecution;
import cn.tsu.edu.o2o.entity.Shop;
import cn.tsu.edu.o2o.enums.ShopStateEnum;
import cn.tsu.edu.o2o.exceptions.ShopOperationException;
import cn.tsu.edu.o2o.service.ShopService;
import cn.tsu.edu.o2o.util.ImageUtil;
import cn.tsu.edu.o2o.util.PageCalculator;
import cn.tsu.edu.o2o.util.PathUtil;

@Service
public class ShopServiceImpl  implements ShopService{
	
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop,ImageHolder thumbnail) {
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
				
				if(thumbnail.getImage()!=null) {
					try {
						//存储图片
						addShopImg(shop, thumbnail);
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
	
	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(thumbnail,dest);
		shop.setShopImg(shopImgAddr);
		
	}

	@Override
	public Shop getByShopId(long shopId) {
         
		return shopDao.queryByShopId(shopId);
	}

	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail)
			throws ShopOperationException {
		//1.判断是否需要处理图片
		if(shop==null ||shop.getShopId()==null)
		{
			return new ShopExecution(ShopStateEnum.NULL_SHOPID);
		}else {
			
			try {
		/*	if(thumbnail.getImage()!=null)
			{
				Shop tempShop=shopDao.queryByShopId(shop.getShopId());
				if(tempShop.getShopId()!=null && fileName!=null && !"".equals(fileName))
				{
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop, shopImgInputStream, fileName);
			}*/
				
			if(thumbnail.getImage()!=null && thumbnail.getImageName()!=null &&!"".equals(thumbnail.getImageName())) {
			
				Shop tempShop=shopDao.queryByShopId(shop.getShopId());
				if(tempShop.getShopImg()!=null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop, thumbnail);
			}
			
			//2.更新店铺信息
			
			shop.setLastEditTime(new Date());
			int effectedNum=shopDao.updateShop(shop);
			if(effectedNum<=0) {
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
			}else {
				shop=shopDao.queryByShopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS,shop);
			}
		}catch(Exception e)
			{
				throw new ShopOperationException("modifyShop error："+e.getMessage());
			}
		}
		
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shops=shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count=shopDao.queryShopCount(shopCondition);
        ShopExecution se=new ShopExecution();
        if(shops!=null) {
        	se.setShopList(shops);
        	se.setCount(count);
        }
        else {
        	se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
		return se;
	}

}
