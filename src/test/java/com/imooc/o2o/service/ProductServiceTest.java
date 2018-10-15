package com.imooc.o2o.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;

public class ProductServiceTest extends BaseTest{
	
	@Autowired
	private ProductService productService;
	
	@Test
	public void testProductDao() throws RuntimeException, IOException {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(15L);
		product.setShop(shop);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(9L);
		product.setProductCategory(productCategory);
		product.setProductName("测试商品1");
		product.setProductName("测试商品2");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		String file1 = new String("D:/javacode/o2omall/img/bg.jpg");
		String file2 = new String("D:/javacode/o2omall/img/big.jpg");
		List<MultipartFile> list=new ArrayList<MultipartFile>();
		try {
			list.add(toMutiPaFile(file2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("图片详细图转换失败"+e.getMessage());
		}
		productService.addProduct(product, toMutiPaFile(file1), list);
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
