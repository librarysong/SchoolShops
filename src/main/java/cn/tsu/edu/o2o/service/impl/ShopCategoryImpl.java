package cn.tsu.edu.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tsu.edu.o2o.dao.ShopCategoryDao;
import cn.tsu.edu.o2o.entity.ShopCategory;
import cn.tsu.edu.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryImpl implements ShopCategoryService {
    
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}

}
