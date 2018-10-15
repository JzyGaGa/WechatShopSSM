package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.ShopCategory;
/**
 * 查询店铺类别接口
 * @author 59842
 *
 */
public interface ShopCategoryDao{
	
	/**
	 * 根据查询条件获取列表  @ param 就直接传入xml层
	 * @param shopCategory
	 * @return
	 */
	List <ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
	
}
