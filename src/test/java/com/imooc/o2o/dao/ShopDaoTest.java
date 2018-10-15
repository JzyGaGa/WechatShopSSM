package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	
	@Autowired
	private ShopDao shopDao;
	
	@Test
	public void testQuerylistcount() {
		Shop shopCondition =new Shop();
		//查询种类
//		ShopCategory shopCategory = new ShopCategory();
//		shopCategory.setShopCategoryId(10L);
//		shopCondition.setShopCategory(shopCategory);
//		List<Shop> list = shopDao.queryShopList(shopCondition, 1, 10);
//		System.out.println(list.size());
//		int count = shopDao.queryShopCount(shopCondition);
//		System.out.println("列表长度"+list.size());
//		System.out.println("取得的数量"+count);
		
		//查询owner
		PersonInfo owner=new PersonInfo();
		owner.setUserId(8L);
		shopCondition.setOwner(owner);
		List<Shop> list = shopDao.queryShopList(shopCondition, 1, 10);
		System.out.println(list.size());
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("列表长度"+list.size());
		System.out.println("取得的数量"+count);
		//查询状态
//		shopCondition.setEnableStatus(0);
//		List<Shop> list = shopDao.queryShopList(shopCondition, 1, 10);
//		System.out.println(list.size());
//		int count = shopDao.queryShopCount(shopCondition);
//		System.out.println("列表长度"+list.size());
//		System.out.println("取得的数量"+count);
		//查询名字模糊查询
//		shopCondition.setShopName("test");
//		List<Shop> list = shopDao.queryShopList(shopCondition, 1, 10);
//		System.out.println(list.size());
//		int count = shopDao.queryShopCount(shopCondition);
//		System.out.println("列表长度"+list.size());
//		System.out.println("取得的数量"+count);
		
	}
	@Test
	@Ignore
	public void testQueryByshopId() {
		long shopid=15;
		Shop shop = shopDao.queryByShopId(shopid);
		System.out.println(shop.getArea().getAreaId());
		System.out.println(shop.getShopCategory().getShopCategoryId());
		System.out.println(shop.getShopId());
	}
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		Area area = new Area();
		PersonInfo personInfo = new PersonInfo();
		ShopCategory shopCategory = new ShopCategory();
		area.setAreaId(3);
		personInfo.setUserId(8L);
		shopCategory.setShopCategoryId(10L);
		shop.setArea(area);
		shop.setOwner(personInfo);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试");
		shop.setShopAddr("测试");
		shop.setShopDesc("测试");
		shop.setPhone("测试");
		shop.setShopImg("测试");
		shop.setPriority(12);
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int res = shopDao.insertShop(shop);
		assertEquals(1, res);
	}
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop();
		Area area = new Area();
		PersonInfo personInfo = new PersonInfo();
		ShopCategory shopCategory = new ShopCategory();
		area.setAreaId(3);
		personInfo.setUserId(8L);
		shopCategory.setShopCategoryId(10L);
		//
		shop.setShopId(32L);
		//
		shop.setArea(area);
		shop.setOwner(personInfo);
		shop.setShopCategory(shopCategory);
		shop.setShopName("修改后");
		shop.setShopAddr("测试");
		shop.setShopDesc("测试");
		shop.setPhone("测试");
		shop.setShopImg("测试");
		shop.setPriority(12);
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int res=shopDao.updateShop(shop);
		assertEquals(1, res);
	}
}
