package com.imooc.o2o.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest{
	
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testGetShopListAndcount() {
		Shop shopCondition =new Shop();
		//查询种类
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(10L);
		shopCondition.setShopCategory(shopCategory);
		ShopExecution se = shopService.getShopListAndcount(shopCondition, 1, 2);
		System.out.println("列表长度"+se.getShoplist().size());
		System.out.println("取得的数量"+se.getCount());
	}
	
	
	@Test
	@Ignore
	public void testModifyShop()  {
		Shop shop = new Shop();
		shop.setShopId(64l);
		String filepath ="D:/javacode/o2omall/img/small.jpg";
		MultipartFile mutiPaFile = null;
		try {
			mutiPaFile = toMutiPaFile(filepath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ShopOperationException("图片转换失败"+e.getMessage());
		}
		
		shopService.modifyShop(shop, mutiPaFile);
		System.out.println(shop.getShopImg());
	}
	@Test
	@Ignore
	public void testAddShop() throws IOException {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		ShopCategory shopCategory = new ShopCategory();
		Area area = new Area();
		owner.setUserId(8L);
		area.setAreaId(3);
		shopCategory.setShopCategoryId(10L);
		shop.setOwner(owner);
		shop.setShopCategory(shopCategory);
		shop.setArea(area);
		shop.setShopAddr("testService");
		shop.setShopName("test店铺Service");
		shop.setShopDesc("testService");
		shop.setShopImg("testService");
		shop.setPhone("testService");
		shop.setPriority(1);
		shop.setCreateTime(new Date());
		shop.setAdvice("审核中");
		String filePath="D:/javacode/o2omall/img/timg.jpg";
		ShopExecution se = shopService.addShop(shop, toMutiPaFile(filePath));
		System.out.println("ShopExecution.state" + se.getState());
		System.out.println("ShopExecution.stateInfo" + se.getStateInfo());
	}
	/**
	 * 将File文件转化为MutipaFile
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public MultipartFile toMutiPaFile(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream input= new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
				IOUtils.toByteArray(input));
		return multipartFile;
		
	}
}
