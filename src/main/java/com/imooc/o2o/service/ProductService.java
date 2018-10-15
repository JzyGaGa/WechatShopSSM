package com.imooc.o2o.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exceptions.ProductOperationException;

public interface ProductService {
	/**
	 * 加入商品
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, MultipartFile thumbnail, List<MultipartFile> productImgs)
			throws RuntimeException;
	
	
	
}
