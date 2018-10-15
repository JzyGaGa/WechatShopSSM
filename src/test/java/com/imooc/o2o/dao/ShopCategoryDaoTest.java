package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{
	
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Test
	public void testShopCategoryDao() {
		
		ShopCategory shopParentCategory = new ShopCategory();
		shopParentCategory.setShopCategoryId(10L);
		ShopCategory shopCategory = new ShopCategory();
//		shopCategory.setParent(shopParentCategory);
		List<ShopCategory> list = shopCategoryDao.queryShopCategory(new ShopCategory());
		assertEquals(18, list.size());
		
	}
}
