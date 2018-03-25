package cn.tsu.edu.o2o.service;

import java.util.List;

import cn.tsu.edu.o2o.entity.ShopCategory;

/**
 * 
 * @author 宋维飞
 *
 */
public interface ShopCategoryService {
   List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
