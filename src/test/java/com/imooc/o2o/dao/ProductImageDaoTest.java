package com.imooc.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductImg;
public class ProductImageDaoTest extends BaseTest{
	
	@Autowired
	private ProductImageDao productImageDao;
	@Test
	public void testProductImageDao() {
		ProductImg productImg = new ProductImg();
		productImg.setProductId(4L);
		productImg.setPriority(100);
		productImg.setImgAddr("454");
		productImg.setImgDesc("zhen tu");
		productImg.setImgAddr("ffdfddafa");
		productImg.setCreateTime(new Date());
		ProductImg productImg1 = new ProductImg();
		productImg1.setProductId(4L);
		productImg1.setPriority(90);
		productImg1.setImgAddr("6666");
		productImg1.setImgDesc("家兔");
		productImg1.setImgAddr("333@@@afa");
		productImg1.setCreateTime(new Date());
		List<ProductImg> list=new ArrayList<ProductImg>();
		list.add(productImg);
		list.add(productImg1);
		productImageDao.batchInsertProductImg(list);
	}
}

