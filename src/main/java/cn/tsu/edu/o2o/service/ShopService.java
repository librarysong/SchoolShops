package cn.tsu.edu.o2o.service;

import java.io.InputStream;

import cn.tsu.edu.o2o.dto.ShopExecution;
import cn.tsu.edu.o2o.entity.Shop;

/**
 * 
 * @author 宋维飞
 *
 */
public interface ShopService {
     ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName);
}
