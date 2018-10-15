package com.imooc.o2o.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
public class ProductDaoTest extends BaseTest{
	
	@Autowired
	private ProductDao productDao;
	@Test
	public void testProductDao() {
		Product product=new Product();
		Shop shop = new Shop();
		shop.setShopId(20l);
		ProductCategory productCategory = new ProductCategory();
	 	productCategory.setProductCategoryId(11l);
		product.setCreateTime(new Date());
		product.setPriority(10);
		product.setProductName("西瓜霜");
		product.setProductDesc("真棒");
		product.setShop(shop);
		product.setProductCategory(productCategory);
		productDao.insertProduct(product);
	}
}
