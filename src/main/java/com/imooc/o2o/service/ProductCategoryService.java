package com.imooc.o2o.service;

import java.util.List;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
/**
 * 查询某个商品的某个类别
 * @author 59842
 *
 */
public interface ProductCategoryService {
	List<ProductCategory> queryProductCategoryList(long shopId);
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> list)
	throws ProductCategoryOperationException;
	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)
	throws ProductCategoryOperationException;
}
