package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductCategory;
/**
 * FixMethodOrder按一定顺序执行
 * @author 59842
 *
 */
@FixMethodOrder
public class ProductCategoryDaoTest extends BaseTest{
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Test
	public void testProductCategoryDao() {
		Long shopId=20L;
		List<ProductCategory> list = productCategoryDao.queryProductCategoryList(shopId);
		System.out.println(list.get(0).getProductCategoryId());
	}
	
	@Test
	@Ignore
	public void testInsertShopDao() {
		ProductCategory pc = new ProductCategory();
		ProductCategory pc1 = new ProductCategory();
		pc.setCreateTime(new Date());
		pc.setPriority(10);
		pc.setProductCategoryName("快乐水");
		pc.setShopId(20L);
		List<ProductCategory> list=new ArrayList<>();
		pc1.setCreateTime(new Date());
		pc1.setPriority(10);
		pc1.setProductCategoryName("冰可乐");
		pc1.setShopId(20L);
		list.add(pc1);
		list.add(pc);
		Integer count = productCategoryDao.batchInsertProductCategory(list);
		System.out.println(count);
	}
	
	@Test
	@Ignore
	public  void testDeleteShopDao() {
		Long productCategoryId=37l;
		Long shopId=20l;
		productCategoryDao.deleteProductCategory(productCategoryId, shopId);
	}
}
