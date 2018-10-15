package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
	 * 查询商店里面商品的种类
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);
	/**
	 * 插入商品种类
	 * @param list
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> list);
	/**
	 * 删除的类别万一下面有商品呢
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId")long shopId); 
}
